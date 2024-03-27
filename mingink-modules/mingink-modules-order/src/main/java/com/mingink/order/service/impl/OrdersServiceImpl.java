package com.mingink.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import com.mingink.common.core.utils.id.SnowFlakeFactory;
import com.mingink.common.redis.service.RedisService;
import com.mingink.order.domain.dto.OrderRequest;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import com.mingink.order.domain.entity.Goods;
import com.mingink.order.domain.entity.Orders;
import com.mingink.order.domain.vo.OrderVO;
import com.mingink.order.mapper.OrdersMapper;
import com.mingink.order.service.IGoodsService;
import com.mingink.order.service.IOrdersService;
import com.mingink.order.service.IPayService;
import com.mingink.system.api.RemoteRoleService;
import com.mingink.system.api.RemoteUserService;
import com.mingink.system.api.domain.entiry.UserRole;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IPayService payService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteRoleService remoteRoleService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Override
    @GlobalTransactional
    public OrderVO createOrder(OrderRequest orderRequest) {
        log.info("创建订单请求参数: {}", orderRequest);
        // 检测userID 和 goodsID 是否存在
        if (remoteUserService.getUserByUserId(orderRequest.getUserId()) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在ID为[" + orderRequest.getUserId() + "]的用户");
        }
        Goods goods = goodsService.getById(orderRequest.getGoodsId());
        if (goods == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在ID为[" + orderRequest.getGoodsId() + "]的商品");
        }
        // 检测商品是否还有库存
        if (goods.getInventory() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "该ID为" + goods.getId() + "商品无剩余库存");
        }
        // 检测商品权限是否永久
        UserRole userRole = remoteRoleService.getUserRoleByIds(orderRequest.getUserId(), goods.getRoleId());
        if (userRole != null && userRole.getIsDurable() == 0) {
            // 该权限已是永久
            log.error("用户已永久拥有该商品权限，无需再次购买");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户已永久拥有该商品权限，无需再次购买");
        }

        // 检测相应订单是否创建(若创建且未过期则直接返回订单信息, 否则传新的订单)
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", orderRequest.getUserId());
        queryWrapper.eq("goods_id", orderRequest.getGoodsId());
        queryWrapper.eq("pay_mode", orderRequest.getPayMode());
        queryWrapper.eq("trade_status", 0);

        Orders order = ordersMapper.selectOne(queryWrapper);
        OrderVO orderVO = new OrderVO();
        if (order == null) {
            // 不存在相关订单，开始创建
            order = new Orders();
            order.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
            order.setUserId(orderRequest.getUserId());
            order.setGoodsId(orderRequest.getGoodsId());
            order.setTotalAmount(goods.getAmount());
            order.setSubject(goods.getSubject());
            order.setPayMode(orderRequest.getPayMode());
            order.setTradeStatus(0);
            order.setCreateTime(LocalDateTime.now());
            order.setExpirationTime(LocalDateTime.now().plusMinutes(30)); // 过期时间为半小时
            ordersMapper.insert(order);
            orderVO.setOrder(order);

            // 生成第三方平台订单及支付界面
            PayParams payParams = new PayParams();
            payParams.setPayMode(order.getPayMode());
            payParams.setTradeNo(order.getId());
            payParams.setTotalAmount(order.getTotalAmount());
            payParams.setSubject(order.getSubject());
            payParams.setExpireTime(order.getExpirationTime());
            payParams.setReturnUrl(orderRequest.getReturnUrl());

            orderVO.setFormPage(payService.getPayPage(payParams));

            // 缓存支付页面
            redisService.setCacheMapValue("orderPayPageMap", order.getId(), orderVO.getFormPage());
            log.info("创建新的订单成功");
        } else {
            orderVO.setOrder(order);
            orderVO.setFormPage(redisService.getCacheMapValue("orderPayPageMap", order.getId()));
            log.info("存在未过期的旧订单，读取旧订单数据");
        }

        // 创建延迟消息, 通过延迟队列让订单三十分钟未支付自动取消
        Message message = MessageBuilder
                .withBody(order.getId().getBytes(StandardCharsets.UTF_8))
                .setHeader("x-delay", 30 * 60 * 1000) // 延迟消息，延迟时间为 30 分钟
                .build();
        // 消息ID，需要封装到CorrelationData中
        CorrelationData correlationData = new CorrelationData(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        // 发送消息
        rabbitTemplate.convertAndSend("delay.direct", "order", message, correlationData);
        log.info("发送订单30分钟后过期消息到mq");

        // 若库存量大于0（限量商品），则该商品库存量-1
        if (goods.getInventory() > 0) {
            goods.setInventory(goods.getInventory() - 1);
            goodsService.updateById(goods);
        }

        return orderVO;
    }

    @SneakyThrows
    @Override
    @GlobalTransactional
    public String payNotify(HttpServletRequest request, Integer payMode) {
        // 处理支付后回调
        PayNotifyResult payNotifyResult = payService.orderPayNotify(request, payMode);

        if (payNotifyResult.getTradeStatus() == 1) {
            // 支付成功, 变更订单状态
            Orders order = ordersMapper.selectById(payNotifyResult.getTradeNo());
            order.setTradeStatus(1);
            order.setBuyerId(payNotifyResult.getBuyerId());
            order.setOutTradeNo(payNotifyResult.getOutTradeNo());
            order.setBuyerPayAmount(payNotifyResult.getBuyerPayAmount());
            order.setReceiptAmount(payNotifyResult.getReceiptAmount());
            order.setPayTime(LocalDateTime.now());
            ordersMapper.updateById(order);

            // 移除 redis 缓存的相关订单页面
            redisService.deleteCacheMapValue("orderPayPageMap", order.getId());

            // 将商品权限授予用户
            Goods goods = goodsService.getById(order.getGoodsId());
            UserRole userRole = remoteRoleService.getUserRoleByIds(order.getUserId(), goods.getRoleId());
            // 检查是否已存在权限
            if (userRole != null) { // 已存在该权限
                // 若权限存在，那么在这里的权限一定是限时的(如果权限时永久的则创建订单的请求不会执行成功)
                if (goods.getEffectiveDuration() == -1) {  // 授予永久权限
                    userRole.setIsDurable(0);
                } else { // 授予非永久权限
                    if (LocalDateTime.now().isBefore(userRole.getExpirationTime())) { // 未过期
                        // 直接新增权限有效时长
                        userRole.setExpirationTime(userRole.getExpirationTime().plusMinutes(goods.getEffectiveDuration()));
                    } else { // 已过期
                        // 在当前时间新增有效时间
                        userRole.setExpirationTime(LocalDateTime.now().plusMinutes(goods.getEffectiveDuration()));
                    }
                }
                // 更新用户权限
                remoteRoleService.updateUserRole(userRole);
            } else { // 不存在该权限
                userRole = new UserRole();
                userRole.setUserId(order.getUserId());
                userRole.setRoleId(goods.getRoleId());
                // 设置权限时长
                if (goods.getEffectiveDuration() == -1) {
                    // 永久权限
                    userRole.setIsDurable(0);
                } else {
                    // 非永久权限
                    userRole.setIsDurable(1); // 非永久权限
                    userRole.setExpirationTime(LocalDateTime.now().plusMinutes(goods.getEffectiveDuration())); // 设置权限过期时间
                }
                // 新增用户权限
                remoteRoleService.addUserRole(userRole);
            }
        }

        return payNotifyResult.getResult();
    }

    @SneakyThrows
    @Override
    @GlobalTransactional
    public Boolean refundPay(String orderId) {
        Orders order = ordersMapper.selectById(orderId);
        Goods goods = goodsService.getById(order.getGoodsId());
        UserRole userRole = remoteRoleService.getUserRoleByIds(order.getUserId(), goods.getRoleId());

        // 判断是否超出可退款条件
        if (userRole.getIsDurable() == 1 && LocalDateTime.now().isAfter(userRole.getExpirationTime())) {
            // 商品权限已过期
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户购买的商品权限已过期，无法退款");
        }

        PayRefundParams payRefundParams = new PayRefundParams();
        payRefundParams.setTradeNo(orderId);
        payRefundParams.setTotalAmount(String.valueOf(order.getTotalAmount()));
        payRefundParams.setOutTradeNo(order.getOutTradeNo());
        payRefundParams.setPayMode(order.getPayMode());
        Boolean isRefundSuccess = payService.orderRefundPay(payRefundParams);

        if (isRefundSuccess) {
            log.info("ID为[{}]的订单退款成功", order.getId());
            // 退款成功，改变订单状态
            order.setTradeStatus(2);
            order.setRefundTime(LocalDateTime.now());
            ordersMapper.updateById(order);

            // 更新库存(+1)
            if (goods.getInventory() >= 0) {
                goods.setInventory(goods.getInventory() + 1);
                goodsService.updateById(goods);
            }

            // 回收用户商品权限
            if (goods.getEffectiveDuration() == -1) {
                if (userRole.getExpirationTime() == null) {
                    // 用户仅购买过永久权限，直接移除该权限
                    remoteRoleService.removeUserRole(userRole.getUserId(), userRole.getRoleId());
                } else {
                    // 用户购买过永久权限和限时权限，保留限时权限，回收永久权限
                    userRole.setIsDurable(1);
                    remoteRoleService.updateUserRole(userRole);
                }
            } else {
                // 回收当前订单的权限时长
                userRole.setExpirationTime(userRole.getExpirationTime().minusMinutes(goods.getEffectiveDuration()));
                remoteRoleService.updateUserRole(userRole);
            }
        }

        return isRefundSuccess;
    }
}
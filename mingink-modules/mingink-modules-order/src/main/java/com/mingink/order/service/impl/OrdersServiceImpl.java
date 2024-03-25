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
import com.mingink.system.api.RemoteUserService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IPayService payService;

    @Autowired
    private RemoteUserService remoteUserService;

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
            // TODO 使用 mq 处理
            PayParams payParams = new PayParams();
            payParams.setPayMode(order.getPayMode());
            payParams.setTradeNo(order.getId());
            payParams.setTotalAmount(order.getTotalAmount());
            payParams.setSubject(order.getSubject());
            payParams.setReturnUrl(orderRequest.getReturnUrl());

            orderVO.setFormPage(payService.getPayPage(payParams));

            // 缓存支付页面
            redisService.setCacheMapValue("orderPayPageMap", order.getId(), orderVO.getFormPage());
            log.info("创建新的订单成功");
        } else {
            orderVO.setFormPage(redisService.getCacheMapValue("orderPayPageMap", order.getId()));
            log.info("存在未过期的旧订单，读取旧订单数据");
        }

        // TODO 创建延迟消息队列 让订单三十分钟未支付自动取消


        return orderVO;
    }

    @SneakyThrows
    @Override
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

            // TODO  移除延迟消息队列中的消息

            // TODO 更新商品库存

            // TODO 将商品权限授予用户
        }

        return payNotifyResult.getResult();
    }

    @SneakyThrows
    @Override
    @GlobalTransactional
    public Boolean refundPay(String orderId) {
        Orders order = ordersMapper.selectById(orderId);

        PayRefundParams payRefundParams = new PayRefundParams();
        payRefundParams.setTradeNo(orderId);
        payRefundParams.setTotalAmount(String.valueOf(order.getTotalAmount()));
        payRefundParams.setOutTradeNo(order.getOutTradeNo());
        payRefundParams.setPayMode(order.getPayMode());
        Boolean isRefundSuccess = payService.orderRefundPay(payRefundParams);

        if (isRefundSuccess) {
            // 退款成功，改变订单状态
            order.setTradeStatus(2);
            order.setRefundTime(LocalDateTime.now());
            ordersMapper.updateById(order);

            // TODO 回收用户商品权限

            // TODO 更新库存
        }

        return isRefundSuccess;
    }
}
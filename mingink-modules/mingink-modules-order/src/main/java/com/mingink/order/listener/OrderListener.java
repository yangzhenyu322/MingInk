package com.mingink.order.listener;

import com.mingink.common.redis.service.RedisService;
import com.mingink.order.domain.entity.Goods;
import com.mingink.order.domain.entity.Orders;
import com.mingink.order.mapper.GoodsMapper;
import com.mingink.order.mapper.OrdersMapper;
import com.mingink.order.service.IPayService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/25 22:02
 */
@Slf4j
@Component
public class OrderListener {
    @Autowired
    private RedisService redisService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private IPayService payService;


    // 延迟队列极限时间 2^32 - 1 毫秒 = 294967295毫秒 ≈ 1193 小时 ≈ 49.7 天
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue.order", durable = "true"),
            exchange = @Exchange(name = "delay.direct", delayed = "true"),
            key = "order"
    ))
    @GlobalTransactional
    public void listenDelayedQueue(String orderId) {
        log.info("接收到 delay.queue.order 的延迟消息，orderId: {}", orderId);
        Orders order = ordersMapper.selectById(orderId);
        if (order == null) {
            return;
        }

        // 1. 检测订单是否过期
        if (order.getTradeStatus() != 0) {
            // 订单在过期前已被处理
            log.info("订单[{}]在过期前已被处理", orderId);
            return;
        }

        // 2. 订单过期，开始处理
        log.info("订单[{}]已过期", orderId);
        // 2.1 移除 redis 缓存的相关订单页面
        redisService.deleteCacheMapValue("orderPayPageMap", orderId);  // 没执行成功？？

        // 2.2 改变订单状态
        order.setTradeStatus(3);
        order.setExpirationTime(LocalDateTime.now());
        ordersMapper.updateById(order);

        // 2.3更新商品库存(+1)
        Goods goods = goodsMapper.selectById(order.getGoodsId());
        if (goods.getInventory() >= 0) {
            goods.setInventory(goods.getInventory() + 1);
            goodsMapper.updateById(goods);
        }
    }
}
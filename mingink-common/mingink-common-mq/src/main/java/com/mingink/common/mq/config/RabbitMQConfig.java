package com.mingink.common.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit MQ 配置
 * @Author: ZenSheep
 * @Date: 2024/3/25 17:00
 */
@Slf4j
@Configuration
public class RabbitMQConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取 RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 消息发送 callback
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack) {
                // 消息发送成功
                log.info("消息发送成功，ID:{}", correlationData.getId());
            } else {
                // 消息发送失败
                log.error("消息发送事变，ID:{}，原因{}", correlationData.getId(), cause);
            }
        }));

        // 设置 ReturnCallback：消息路由失败时的 callback
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            // 判断是否延迟消息
            if (returnedMessage.getMessage().getMessageProperties().getReceivedDelay() > 0) {
                // 这是延迟消息，忽略这个错误提示
                return;
            }

            // 投递失败，记录日志
            log.error("消息路由失败，应答码{}，原因{}，交换机{}，路由键{}，消息{}",
                    returnedMessage.getReplyCode(), returnedMessage.getReplyText(), returnedMessage.getExchange(), returnedMessage.getRoutingKey(), returnedMessage.getMessage().toString());
            // 如果有业务需要，可以重发消息
        });
    }
}

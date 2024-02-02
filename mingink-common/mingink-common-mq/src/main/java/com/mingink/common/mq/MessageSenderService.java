package com.mingink.common.mq;

import com.mingink.common.mq.model.TopicMessage;
import org.apache.rocketmq.client.producer.TransactionSendResult;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 21:52
 * @description 消息队列发送服务
 */
public interface MessageSenderService {
    /**
     * 发送消息
     * @param message 发送的消息
     */
    boolean send(TopicMessage message);

    /**
     * 发送事务消息，主要是RocketMQ
     * @param message 事务消息
     * @param arg 其他参数
     * @return 返回事务发送结果
     */
    default TransactionSendResult sendMessageInTransaction(TopicMessage message, Object arg){
        return null;
    }

}


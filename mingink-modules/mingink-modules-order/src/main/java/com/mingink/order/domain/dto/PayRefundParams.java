package com.mingink.order.domain.dto;

import lombok.Data;

/**
 * 退款参数
 * @Author: ZenSheep
 * @Date: 2024/3/24 21:58
 */
@Data
public class PayRefundParams {
    // 订单号
    private String tradeNo;

    // 订单总金额
    private String totalAmount;

    // 支付平台回调的订单流水号
    private String outTradeNo;

    // 支付方式
    private Integer payMode;
}
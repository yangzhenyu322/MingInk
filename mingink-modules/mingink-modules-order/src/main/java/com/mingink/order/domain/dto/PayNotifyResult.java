package com.mingink.order.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/24 17:40
 */
@Data
public class PayNotifyResult {
    // 商户订单号
    private String tradeNo;

    // 支付平台交易凭证号
    private String outTradeNo;

    // 回调后交易状态
    private Integer tradeStatus;

    // 买家在支付平台的唯一ID
    private String buyerId;

    // 买家付款金额
    private BigDecimal buyerPayAmount;

    // 实收金额
    private BigDecimal receiptAmount;

    // 返回给支付平台的回调信息
    private String result;
}

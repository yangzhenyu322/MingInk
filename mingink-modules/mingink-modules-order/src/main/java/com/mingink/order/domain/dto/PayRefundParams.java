package com.mingink.order.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 退款参数
 * @Author: ZenSheep
 * @Date: 2024/3/24 21:58
 */
@SuppressWarnings("all")
@Data
public class PayRefundParams implements Serializable {
    private static final long serialVersionUID = 1L;
    // 订单号
    private String tradeNo;// 订单总金额
    private String totalAmount;

    // 支付平台回调的订单流水号
    private String outTradeNo;

    // 支付方式
    private Integer payMode;

    // 退款订单号（商家订单号）
    private String outRefundNo;

    // 退款资金来源
    private String fundsAccount;

    // 退款原因
    private String reason;

    private Amount amount;

    private GoodDetail[] goodDetails;

    /**
     * 金额信息，必填
     */
    @Data
    public class Amount {
        private Integer refund;
        private From[] froms;
        private Integer total; // 单位为分
        private String currency;

        @Data
        public class From {
            private String account;
            private Integer amount;
        }
    }

    /**
     * 退款商品，选填
     */
    @Data
    private class GoodDetail {
        private String merchantGoodsId;
        // 商品编码，可空
        private String wechatpayGoodsId;
        // 商品名称，可空
        private String goodsName;
        // 商品单价，必填
        private Integer unitPrice;
        // 退款金额，必填
        private Integer refundAmount;
        // 退款数量，必填
        private Integer refundQuantity;
    }
}





package com.mingink.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
@Data
@TableName("orders")
@Schema(description = "订单")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    @Schema(description = "商户订单号，由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。如20150320010101001")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "商品ID")
    private String goodsId;

    @Schema(description = "订单总金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00")
    private BigDecimal totalAmount;

    @Schema(description = "订单标题，如Iphone6 16G")
    private String subject;

    @Schema(description = "支付方式，0-微信、1-支付宝")
    private Integer payMode;

    @Schema(description = "订单状态，0-创建、1-已支付、2-退款、3-过期")
    private Integer tradeStatus;

    @Schema(description = "支付平台交易凭证号")
    private String outTradeNo;

    @Schema(description = "买家在支付平台的唯一ID")
    private String buyerId;

    @Schema(description = "买家付款金额")
    private BigDecimal buyerPayAmount;

    @Schema(description = "实收金额（订单总金额 - 平台手续费）")
    private BigDecimal receiptAmount;

    @Schema(description = "订单创建时间")
    private LocalDateTime createTime;

    @Schema(description = "订单支付时间")
    private LocalDateTime payTime;

    @Schema(description = "订单退款时间")
    private LocalDateTime refundTime;

    @Schema(description = "订单过期时间")
    private LocalDateTime expirationTime;
}

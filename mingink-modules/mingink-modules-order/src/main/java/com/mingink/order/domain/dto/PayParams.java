package com.mingink.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/22 22:24
 */
@Data
@Schema(description = "支付请求参数")
public class PayParams {
    private static final long serialVersionUID = 1L;

    @Schema(description = "支付方式，0-微信、1-支付宝")
    private Integer payMode;

    @Schema(description = "商户订单号（必填），由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。如20150320010101001")
    private String tradeNo;

    @Schema(description = "订单总金额（必填），单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00 ")
    private BigDecimal totalAmount;

    @Schema(description = "订单标题（必填），如Iphone6 16G")
    private String subject;

    @Schema(description = "返回路径，支付后前端跳转 url 链接，要求该前端页面能够被公网访问，开发过程可以使用netapp内网穿透，例如  http://zensheep.natapp1.cc/dashboard/workbench，zensheep.natapp1.cc表示访问域名，/dashboard/workbench表示前端页面路由")
    private String returnUrl;
}

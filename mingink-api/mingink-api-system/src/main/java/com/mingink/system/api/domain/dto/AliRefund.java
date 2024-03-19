package com.mingink.system.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 支付宝退款参数
 * @Author: ZenSheep
 * @Date: 2024/3/18 16:50
 */
@Data
@Schema(description = "支付宝退款 请求参数")
public class AliRefund {
    @Schema(description = "商户订单号，由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。如20150320010101001")
    private String traceNo;

    @Schema(description = "订单总金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00 ")
    private String totalAmount;

    @Schema(description = "支付宝回调的订单流水号")
    private String alipayTraceNo;
}

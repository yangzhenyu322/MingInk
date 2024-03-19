package com.mingink.system.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 支付宝支付 请求参数
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:14
 */
@Data
@Schema(description = "支付宝支付 请求参数")
public class AliPay implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商户订单号，由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。如20150320010101001")
    private String traceNo;

    @Schema(description = "订单总金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00 ")
    private String totalAmount;

    @Schema(description = "订单标题，如Iphone6 16G")
    private String subject;

    @Schema(description = "销售产品码，商家和支付宝签约的产品码。注：目前电脑支付场景下仅支持FAST_INSTANT_TRADE_PAY")
    private String productCode;

    @Schema(description = "PC扫码支付的方式, 支持前置模式和跳转模式。\n" +
            "前置模式是将二维码前置到商户的订单确认页的模式。需要商户在自己的页面中以 iframe 方式请求支付宝页面。具体支持的枚举值有以下几种：\n" +
            "0：订单码-简约前置模式，对应 iframe 宽度不能小于600px，高度不能小于300px；\n" +
            "1：订单码-前置模式，对应iframe 宽度不能小于 300px，高度不能小于600px；\n" +
            "3：订单码-迷你前置模式，对应 iframe 宽度不能小于 75px，高度不能小于75px；\n" +
            "4：订单码-可定义宽度的嵌入式二维码，商户可根据需要设定二维码的大小。\n" +
            "跳转模式下，用户的扫码界面是由支付宝生成的，不在商户的域名下。支持传入的枚举值有：\n" +
            "2：订单码-跳转模式, 参考：https://blog.csdn.net/weixin_47284756/article/details/122602293")
    private String arPayMode;
}
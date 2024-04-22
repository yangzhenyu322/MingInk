package com.mingink.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/4/13 10:53
 * @description 微信支付配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayConfig {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付的API密钥
     */
    private String mchKey;

    /**
     * 商户号密钥V3
     */
    private String apiV3Key;

    /**
     * 应用对应的凭证
     */
    private String appSecret;

    /**
    * 商户号退款证书地址
    */
    private String apiClientCert;

    /**
     * 商户API证书的证书序列号
     */
    private String merchantSerialNumber;


    /**
     * 【退款结果回调url】 异步接收微信支付退款结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     *  如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效，优先回调当前传的这个地址。
     */
    // TODO 回调地址待填
    public static String notifyUrl = "";

}

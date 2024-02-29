package com.mingink.system.config;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BaiDu SMS（Short Message Service，短讯服务）-Client配置
 * @Author: ZenSheep
 * @Date: 2024/2/3 20:22
 */
@Configuration
public class BaiDuSMSConfiguration {
    private static String accessKeyId;

    private static String secretAccessKey;

    // 短信签名Id
    private static String signatureId;

    // 短信模板Id
    private static String template;

    // 产品域名
    private static String endpoint;

    private volatile static SmsClientConfiguration smsClientConfiguration;

    private volatile static SmsClient smsClient;

    @Value("${baidu.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        BaiDuSMSConfiguration.accessKeyId = accessKeyId;
    }

    @Value("${baidu.secretAccessKey}")
    public void setSecretAccessKey(String secretAccessKey) {
        BaiDuSMSConfiguration.secretAccessKey = secretAccessKey;
    }

    @Value("${baidu.sms.signatureId}")
    public void setSignatureId(String signatureId) {
        BaiDuSMSConfiguration.signatureId = signatureId;
    }

    public static String getSignatureId() {
        return signatureId;
    }

    @Value("${baidu.sms.template}")
    public void setTemplate(String template) {
        BaiDuSMSConfiguration.template = template;
    }

    public static String getTemplate() {
        return template;
    }

    @Value("${baidu.sms.endpoint}")
    public void setEndpoint(String endpoint) {
        BaiDuSMSConfiguration.endpoint = endpoint;
    }

    public static SmsClientConfiguration smsClientConfiguration() {
        if (smsClientConfiguration == null) {
            synchronized (SmsClientConfiguration.class) {
                if (smsClientConfiguration == null) {
                    smsClientConfiguration = new SmsClientConfiguration();
                    smsClientConfiguration.setCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKey));
                    smsClientConfiguration.setEndpoint(endpoint);
                }
            }
        }

        return smsClientConfiguration;
    }

    @Bean
    public static SmsClient smsClient() {
        if (smsClient == null) {
            synchronized (BaiDuSMSConfiguration.class) {
                if (smsClient == null) {
                    smsClient = new SmsClient(smsClientConfiguration());
                }
            }
        }

        return smsClient;
    }
}
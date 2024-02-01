package com.mingink.system.config;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SMS（Short Message Service，短讯服务）-Client配置
 * @Author: ZenSheep
 * @Date: 2024/2/1 20:28
 */
@Configuration
public class SMSConfiguration {
    private static String accessKeyId;

    private static String accessKeySecret;

    // 短信签名名称
    private static String signName;

    // 短信模板变量对应的实际值
    private static String templateCode;

    // Region ID
    private static String region;

    // 产品域名
    public static String endpoint;

    // Configure Credentials authentication info, including ak, secret, token
    private volatile static StaticCredentialProvider provider;

    // Configure the Client
    private volatile static AsyncClient smsClient;

    @Value("${aliyun.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        SMSConfiguration.accessKeyId = accessKeyId;
    }

    @Value("${aliyun.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        SMSConfiguration.accessKeySecret = accessKeySecret;
    }

    @Value("${aliyun.sms.signName}")
    public void setSignName(String signName) {
        SMSConfiguration.signName = signName;
    }

    public static String getSignName() {
        return signName;
    }

    @Value("${aliyun.sms.templateCode}")
    public void setTemplateCode(String templateCode) {
        SMSConfiguration.templateCode = templateCode;
    }

    public static String getTemplateCode() {
        return templateCode;
    }

    @Value("${aliyun.sms.region}")
    public void setRegion(String region) {
        SMSConfiguration.region = region;
    }

    @Value("${aliyun.sms.endpoint}")
    public void setEndpoint(String endpoint) {
        SMSConfiguration.endpoint = endpoint;
    }

    public static StaticCredentialProvider provider() {
        if (provider == null) {
            synchronized (SMSConfiguration.class) {
                if (provider == null) {
                    provider = StaticCredentialProvider.create(Credential.builder()
                            .accessKeyId(accessKeyId)
                            .accessKeySecret(accessKeySecret)
                            .build());
                }
            }
        }

        return provider;
    }

    @Bean
    public static AsyncClient smsClient() {
        if (smsClient == null) {
            synchronized (SMSConfiguration.class) {
                if (smsClient == null) {
                    smsClient = AsyncClient.builder()
                            .region(region)
                            .credentialsProvider(provider())
                            .overrideConfiguration(
                                    ClientOverrideConfiguration.create()
                                            .setEndpointOverride(endpoint)
                            ).build();
                }
            }
        }

        return smsClient;
    }
}

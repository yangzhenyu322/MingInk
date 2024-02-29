package com.mingink.article.config;

import com.mingink.article.domain.pojo.GorseClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gorse 配置类
 * @Author: ZenSheep
 * @Date: 2024/2/29 16:09
 */
@Configuration
public class GorseConfiguration {
    // Gorse服务Url
    private static String endpoint;

    // Api Key
    private static String apiKey;

    private volatile static GorseClient gorseClient;

    public static String getEndpoint() {
        return endpoint;
    }

    @Value("${gorse.endpoint}")
    public void setEndpoint(String endpoint) {
        GorseConfiguration.endpoint = endpoint;
    }

    public static String getApiKey() {
        return apiKey;
    }

    @Value("${gorse.apiKey}")
    public void setApiKey(String apiKey) {
        GorseConfiguration.apiKey = apiKey;
    }

    @Bean
    public static GorseClient gorse() {
        if (gorseClient == null) {
            synchronized (GorseConfiguration.class) {
                if (gorseClient == null) {
                    gorseClient = new GorseClient(endpoint, apiKey);
                }
            }
        }

        return gorseClient;
    }
}

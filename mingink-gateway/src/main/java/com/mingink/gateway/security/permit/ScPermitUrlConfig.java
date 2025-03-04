package com.mingink.gateway.security.permit;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义放开配置的URL
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:34
 */
@Component
public class ScPermitUrlConfig {
    // 默认前缀
    public static final String CONTEXT_PATH = "/api/v1";

    /**
     * 带默认前缀无需认证授权的urls
     */
    private String[] permitUrlWithPrefix = {
            "/user/register/**",
            "/user/password/**",
            "/oauth/**",
            "/sms/**",
            "/order/**",
            "/user/smsLogin/**"
    };

    /**
     * 不带前缀无需认证授权的urls
     */
    private String[] permitUrl = {
            // swagger
            "/doc.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/favicon.ico"
            //  "/heartbeat/**"
    };

    /**
     * 额外放开权限的url
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public String[] permit(String... urls) {
        Set<String> set = new HashSet<>();
        if (urls.length > 0) {
            Collections.addAll(set, addContextPath(urls));
        }

        // 放开权限的地址
        Collections.addAll(set, addContextPath(permitUrlWithPrefix));
        Collections.addAll(set, permitUrl);

        return set.toArray(new String[set.size()]);
    }

    /**
     * 地址加访问前缀
     * @param urls
     * @return
     */
    private String[] addContextPath(String[] urls) {
        for (int i = 0; i < urls.length; i++) {
            urls[i] = CONTEXT_PATH + urls[i];
        }

        return urls;
    }
}
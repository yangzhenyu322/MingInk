package com.mingink.gateway.security.handler;

import com.alibaba.fastjson2.JSON;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.common.redis.service.RedisService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 登录认证成功类
 * @Author: ZenSheep
 * @Date: 2024/1/4 21:19
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    private int timeout = 60 * 60 * 24; // 默认24h

    @Autowired
    private RedisService redisService;

    /**
     * 登录成功处理
     * @param webFilterExchange
     * @param authentication
     * @return
     */
    @SneakyThrows
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();

        // 设置body
        HashMap<String, Object> map = new HashMap<>();
        boolean isRememberMe = Boolean.parseBoolean(exchange.getRequest().getHeaders().getFirst("REMEMBER_ME"));

        log.info(authentication.toString());
        log.info("isRememberMe:{}", isRememberMe);

        List<? extends GrantedAuthority> list = new ArrayList<>(authentication.getAuthorities());
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("username", authentication.getName());
            // 将多个roleKey以JSON字符串形式存储到token中
            String rolesJsonStr = JSON.toJSONString(list.stream().map(authority -> {
                return authority.getAuthority();
            }).collect(Collectors.toList()));

            payload.put("roleKeys", rolesJsonStr);

            String token;
            if (isRememberMe) {
                token = JWTUtils.creatToken(payload, 60 * 60 * 24 * JWTUtils.REMEMBER_ME); // 创建token，用户勾选"请记住我时"，token的过期时间设置为7天
                response.addCookie(ResponseCookie.from("token", token).maxAge(Duration.ofDays(JWTUtils.REMEMBER_ME)).path("/").build());
                redisService.setCacheObject(authentication.getName(), token, JWTUtils.REMEMBER_ME, TimeUnit.DAYS); // 保存7天
            } else {
                token = JWTUtils.creatToken(payload, 60 * 60 * 24 * 1); // 创建token，过期时间设置为24h
                response.addCookie(ResponseCookie.from("token", token).maxAge(Duration.ofDays(1)).path("/").build());
                // maxAge默认-1 浏览器关闭cookie失效
                redisService.setCacheObject(authentication.getName(), token, 1, TimeUnit.DAYS);
            }

            map.put("code", HttpStatus.OK.value());
            map.put("msg", "登录成功");
            map.put("token", token);
            log.info("new token:" + token);
            log.info("sign in success!");
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", HttpStatus.FORBIDDEN.value());
            map.put("msg", "登录失败");
            log.info("sign in failed!");
        }

        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}

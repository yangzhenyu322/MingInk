package com.mingink.gateway.security;

import com.alibaba.fastjson2.JSON;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 认证信息存储管理(用户信息上下文存储类)
 * 1. 把header拿到的token放入AuthenticationToken
 *
 * @Author: ZenSheep
 * @Date: 2023/12/28 15:16
 */
@Slf4j
@Component
public class ScSecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("new access request path={}", exchange.getRequest().getPath());

        // 从请求头的AUTHORIZATION获取token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("authorization = {}", token);

        if (token != null) {
            try {
                // 解码token信息
                Map<String, Object> userMap = JWTUtils.getTokenInfo(token);
                log.info("username:{}", userMap.get("username"));
                // 通过用户名从redis缓存获取对应token
                String redisToken = redisService.getCacheObject((String) userMap.get("username"));
                if (redisToken == null || !redisToken.equals(token)) {
                    // token过期或token不正确
                    return Mono.empty();
                }

                // token校验成功，进行授权
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                List<String> roleKeys = JSON.parseArray((String) userMap.get("roleKeys"), String.class);
                roleKeys.stream().forEach(roleKey -> authorities.add(new SimpleGrantedAuthority(roleKey)));

                log.info("role = {}", (String) userMap.get("role"));

                Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);

                // 仍存在旧的有效令牌, 则刷新令牌（主要是刷新过期时间）(存在bug)
//                String newToken;
//
//                Map<String, String> payload = new HashMap<>();
//                payload.put("username", (String) userMap.get("username"));
//                payload.put("role", (String) userMap.get("role"));
//
//                boolean isRememberMe = Boolean.parseBoolean(exchange.getRequest().getHeaders().getFirst("REMEMBER_ME"));
//                ServerHttpResponse response = exchange.getResponse();
//                if (!isRememberMe) {
//                    newToken = JWTUtils.creatToken(payload, 60 * 60 * 24); // 创建token，过期时间设置为24h
//                    response.addCookie(ResponseCookie.from("token", newToken).path("/").build());
//                    // maxAge默认-1 浏览器关闭cookie失效
//                    redisTemplate.opsForValue().set((String) userMap.get("username"), newToken, 1, TimeUnit.DAYS);
//                } else {
//                    newToken = JWTUtils.creatToken(payload, 60 * 60 * 24 * JWTUtils.REMEMBER_ME); // 创建token，用户勾选"请记住我时"，token的过期时间设置为7天
//                    response.addCookie(ResponseCookie.from("token", newToken).maxAge(Duration.ofDays(JWTUtils.REMEMBER_ME)).path("/").build());
//                    redisTemplate.opsForValue().set((String) userMap.get("username"), newToken, JWTUtils.REMEMBER_ME, TimeUnit.DAYS); // 保存7天
//                }

                securityContext.setAuthentication(authentication);
                return Mono.just(securityContext);
            } catch (Exception e) {
                e.printStackTrace();
                return Mono.empty();
            }
        }

        return Mono.empty();
    }
}
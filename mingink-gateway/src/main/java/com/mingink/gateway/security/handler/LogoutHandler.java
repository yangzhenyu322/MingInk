package com.mingink.gateway.security.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 登出处理类
 * @Author: ZenSheep
 * @Date: 2024/1/5 14:15
 */
@Slf4j
@Component
public class LogoutHandler implements ServerLogoutHandler {
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> logout(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("LogoutHandler.logout()");
        HttpCookie cookie = webFilterExchange.getExchange().getRequest().getCookies().getFirst("token");
        try {
            if (cookie != null) {
                Map<String, Object> userMap = JWTUtils.getTokenInfo(cookie.getValue());
//                redisTemplate.delete((String) userMap.get("username"));
                redisService.deleteObject((String) userMap.get("username"));
                log.info("remove token");
            }
        } catch (JWTDecodeException e) {
            return Mono.error(e);
        }

        return Mono.empty();
    }
}

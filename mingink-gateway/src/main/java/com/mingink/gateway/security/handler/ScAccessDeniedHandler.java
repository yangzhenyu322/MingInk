package com.mingink.gateway.security.handler;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 鉴权失败执行 Handler
 * @Author: ZenSheep
 * @Date: 2023/12/28 14:53
 */
@Slf4j
@Component
public class ScAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "未授权禁止访问");

        log.error("authorization access forbidden path={}", exchange.getRequest().getPath());

        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(map));
        return response.writeWith(Mono.just(dataBuffer));
    }
}

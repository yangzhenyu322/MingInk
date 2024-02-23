package com.mingink.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权（访问授权）管理
 * 3. 权限认证，是否放行
 *
 * @Author: ZenSheep
 * @Date: 2023/12/28 15:37
 */
@Slf4j
@Component
public class ScAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        log.info("ScAuthorizationManager.check()");

        return authentication.map(auth -> {
            // 鉴权成功 -> CookieToHeadersFilter设置请求头 -> 访问服务接口
            // 鉴权失败：-> ScAuthenticationEntryPoint -> ScAccessDeniedHandler

            //SecurityUserDetails userSecurity = (SecurityUserDetails) auth.getPrincipal();
            // 请求路径
            String path = authorizationContext.getExchange().getRequest().getURI().getPath();
            log.info("Authority url:{}", path);
            // roleKeys
            List<String> roleKeys = auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
            log.info("roleKeys:{}", roleKeys);

            // 鉴权：根据请求路径和用户角色进行鉴权
            if (path.contains("admin")) {
                // admin权限
                return new AuthorizationDecision(roleKeys.contains("admin"));
            }

            if (path.contains("vip")) {
                // vip权限
                return new AuthorizationDecision(roleKeys.contains("vip"));
            }

            // common 权限
            return new AuthorizationDecision(true);
        }).defaultIfEmpty(new AuthorizationDecision(false)); // 无任何权限（未登录） ——> AcAuthenticationEntryPoint(只有这里才仅进入AcAuthenticationEntryPoint，而不进入ScAccessDeniedHandler)
    }
}
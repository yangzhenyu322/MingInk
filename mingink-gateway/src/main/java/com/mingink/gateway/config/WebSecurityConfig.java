package com.mingink.gateway.config;

import com.mingink.gateway.security.ScAuthorizationManager;
import com.mingink.gateway.security.ScSecurityContextRepository;
import com.mingink.gateway.security.filter.CookieToHeadersFilter;
import com.mingink.gateway.security.handler.*;
import com.mingink.gateway.security.permit.ScPermitUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * WebSecurityConfig 核心配置
 * @Author: ZenSheep
 * @Date: 2024/2/1 18:34
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    @Autowired
    CookieToHeadersFilter cookieToHeadersFilter;

    @Autowired
    ScSecurityContextRepository scSecurityContextRepository;

    @Autowired
    ScAuthorizationManager scAuthorizationManager;

    @Autowired
    ScPermitUrlConfig scPermitUrlConfig;

    @Autowired
    ScAccessDeniedHandler scAccessDeniedHandler;

    @Autowired
    ScAuthenticationEntryPoint scAuthenticationEntryPoint;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthenticationFailHandler authenticationFailHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     *
     * 处理链路：
     *      login : CookieToHeadersFilter -> ScSecurityContextRepository -> ScAuthenticationManager（优先级高于  SecurityUserDetailsService）  -> AuthenticationSuccessHandler/AuthenticationFailHandler
     *      logout: CookieToHeadersFilter -> ScSecurityContextRepository -> LogoutHandler -> LogoutSuccessHandler
     *      未登录进行 url request: CookieToHeadersFilter -> ScSecurityContextRepository -> ScAuthorizationManager -> ScAuthenticationEntryPoint
     *      登录后进行url request: CookieToHeadersFilter -> ScSecurityContextRepository -> ScAuthorizationManager -> CookieToHeadersFilter（子线程, 可以在前面ScSecurityContextRepository更新token并重新设置请求头）-> 服务接口
     *      鉴权失败: CookieToHeadersFilter -> ScSecurityContextRepository -> ScAuthorizationManager -> ScAuthenticationEntryPoint -> ScAccessDeniedHandler
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // 将Cookie写入Http请求头中，SecurityWebFiltersOrder枚举类定义了执行次序
        http.addFilterBefore(cookieToHeadersFilter, SecurityWebFiltersOrder.HTTP_HEADERS_WRITER);

        http
            // 存储认证信息
            .securityContextRepository(scSecurityContextRepository)
            //请求拦截处理
            .authorizeExchange(exchange -> exchange // 请求拦截处理
                    .pathMatchers(scPermitUrlConfig.permit()).permitAll() // 默认放开的地址
                    .pathMatchers(HttpMethod.OPTIONS).permitAll() // 放开的请求方法
                    .anyExchange().access(scAuthorizationManager) // 其它的地址走后续验证
            )
            // 登录接口
            .httpBasic()
            .and()
            .formLogin().loginPage("/api/v1/login") // 在ScAuthorizationManager的authenticate处理登录请求
            .authenticationSuccessHandler(authenticationSuccessHandler) //认证成功
            .authenticationFailureHandler(authenticationFailHandler) // 认证失败
            .and()
            .exceptionHandling().authenticationEntryPoint(scAuthenticationEntryPoint) // 未认证访问服务接口处理
            .and()
            .exceptionHandling().accessDeniedHandler(scAccessDeniedHandler) // 授权失败
            .and()
            // 登出接口
            .logout().logoutUrl("/api/v1/logout")
            .logoutHandler(logoutHandler) // 登出处理
            .logoutSuccessHandler(logoutSuccessHandler)  // 登出成功处理
            .and()
            // 跨域配置
            .cors()
            .configurationSource(corsConfigurationSource())
            // 关闭csrf防护，防止用户无法被认证
            .and().csrf().disable();

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 允许哪些网站的跨域请求
        corsConfiguration.addAllowedOriginPattern("*");
        // 允许所有请求方法
        corsConfiguration.addAllowedMethod("*");
        // 允许所有域，当请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许携带 Authorization 头
        corsConfiguration.setAllowCredentials(true);
        // 允许全部请求路径
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}

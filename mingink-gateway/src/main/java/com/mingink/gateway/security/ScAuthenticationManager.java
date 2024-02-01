package com.mingink.gateway.security;

import com.mingink.gateway.remote.ReactiveRemoteRoleService;
import com.mingink.gateway.remote.ReactiveRemoteUserService;
import com.mingink.gateway.security.domain.SecurityUserDetails;
import com.mingink.system.api.domain.Role;
import com.mingink.system.api.domain.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 登录认证管理
 * 2. 从AuthenticationToken读取Token并做用户数据解析
 *
 * @Author: ZenSheep
 * @Date: 2023/12/28 15:19
 */
@Slf4j
@Component
public class ScAuthenticationManager implements ReactiveAuthenticationManager {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ReactiveRemoteUserService reactiveRemoteUserService;

    @Autowired
    private ReactiveRemoteRoleService reactiveRemoteRoleService;

    /**
     * 认证
     * @param authentication
     * @return
     */
    @SneakyThrows
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 校验token
        log.info("ScAuthenticationManager.authenticate()");
        String username = authentication.getName(); // 获取用户提供的用户名（账号）
        log.info("username:{}", username);
        String password = authentication.getCredentials().toString(); //获取用户提供的密码
        log.info("password:{}", password);

        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        // 调用数据库根据用户名获取用户(需要保证用户名唯一)
        User user = reactiveRemoteUserService.findUserByUserName(username).get();

        if (user == null) {
            // 用户名错误或不存在
            throw new UsernameNotFoundException("用户不存在");
        }

        if (!password.equals(user.getPassword())) {
            throw new UsernameNotFoundException("用户密码不正确");
        }

        // 登录成功，进行授权
        Role sysRole = reactiveRemoteRoleService.getSysRoleById(user.getRoleId()).get();
        String role = sysRole.getRoleKey();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        // 返回securityUserDetails对象后会自定校验用户密码是否正确
        SecurityUserDetails securityUserDetails = new SecurityUserDetails(username, "{bcrypt}" + passwordEncoder.encode(user.getPassword()), authorities, user.getUserId());

        return Mono.just(new UsernamePasswordAuthenticationToken(securityUserDetails, securityUserDetails.getPassword(), securityUserDetails.getAuthorities()));
    }
}
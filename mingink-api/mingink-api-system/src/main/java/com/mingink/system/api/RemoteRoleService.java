package com.mingink.system.api;

import com.mingink.system.api.config.DefaultFeignConfiguration;
import com.mingink.system.api.domain.entiry.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Role Feign：提供Role的远程服务
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:07
 */
@FeignClient(name = "mingink-system", contextId = "remote-role", configuration = DefaultFeignConfiguration.class)
public interface RemoteRoleService {
    @GetMapping("/role/userId/{userId}")
    public List<Role> getRolesByUserId(@PathVariable("userId") String userId);
}
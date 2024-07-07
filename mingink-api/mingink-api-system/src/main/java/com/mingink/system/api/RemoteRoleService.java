package com.mingink.system.api;

import com.mingink.system.api.config.DefaultFeignConfiguration;
import com.mingink.system.api.domain.entity.Role;
import com.mingink.system.api.domain.entity.UserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/role/userId/{userId}/roleId/{roleId}")
    public UserRole getUserRoleByIds(@PathVariable("userId") String userId, @PathVariable("roleId") Long roleId);

    @PostMapping("/role/new")
    public Boolean addUserRole(@RequestBody UserRole userRole);

    @PutMapping("/role/update")
    public Boolean updateUserRole(@RequestBody UserRole userRole);

    @DeleteMapping("/role/userId/{userId}/roleId/{roleId}")
    public Boolean removeUserRole(@PathVariable("userId") String userId, @PathVariable("roleId") Long roleId);
}
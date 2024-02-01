package com.mingink.system.api;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.config.DefaultFeignConfiguration;
import com.mingink.system.api.domain.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Role Feign：提供Role的远程服务
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:07
 */
@FeignClient(name = "mingink-system", contextId = "remote-role", configuration = DefaultFeignConfiguration.class)
public interface RemoteRoleService {
    @GetMapping("/role/id/{id}")
    public Role getSysRoleById(@PathVariable("id") Long id);
}

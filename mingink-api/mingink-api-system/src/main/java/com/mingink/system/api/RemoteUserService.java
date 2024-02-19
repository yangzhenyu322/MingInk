package com.mingink.system.api;

import com.mingink.system.api.config.DefaultFeignConfiguration;
import com.mingink.system.api.domain.UserSafeInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * User Feign：提供User的远程服务
 * @Author: ZenSheep
 * @Date: 2024/2/1 18:59
 */
@FeignClient(name = "mingink-system", contextId = "remote-user", configuration = DefaultFeignConfiguration.class)
public interface RemoteUserService {
    @GetMapping("/user/username/{username}")
    public List<UserSafeInfo> getUserByUserName(@PathVariable("username") String username);
}
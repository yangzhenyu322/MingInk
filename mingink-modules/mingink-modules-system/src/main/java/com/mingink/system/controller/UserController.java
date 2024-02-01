package com.mingink.system.controller;

import com.mingink.common.core.domain.R;
import com.mingink.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务接口
 * @Author: ZenSheep
 * @Date: 2024/2/1 12:03
 */
@RefreshScope
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/test")
    public R<?> test() {
        return R.ok("test");
    }

    @GetMapping("/all")
    public R<?> getAllUsers() {
        return userService.getUserList();
    }
}

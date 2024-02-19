package com.mingink.system.controller;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.Role;
import com.mingink.system.service.IRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role服务接口
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:14
 */
@RestController
@RequestMapping("role")
@Api(value = "角色接口功能", tags = "RoleController", description = "角色接口相关介绍")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 通过用户id获取指定Role对象
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    public Role getRoleById(@PathVariable("id") Long id) {
        return roleService.getRoleById(id);
    }
}
package com.mingink.system.controller;

import com.mingink.system.api.domain.entiry.Role;
import com.mingink.system.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Role服务接口
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:14
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色接口")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 通过用户id获取该用户的所有Role对象
     * @param userId
     * @return
     */
    @GetMapping("/userId/{userId}")
    @Operation(hidden = true)
    public List<Role> getRolesByUserId(@PathVariable("userId") String userId) {
        return roleService.getRolesByUserId(userId);
    }
}
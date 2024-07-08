package com.mingink.system.controller;

import com.mingink.system.api.domain.entity.Role;
import com.mingink.system.api.domain.entity.UserRole;
import com.mingink.system.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userId/{userId}/roleId/{roleId}")
    @Operation(hidden = true)
    public UserRole getUserRoleByIds(@PathVariable("userId") String userId,
                                     @PathVariable("roleId") Long roleId) {
        return roleService.getUserRoleByIds(userId, roleId);
    }

    /**
     * 添加新的用户权限
     * @param userRole
     * @return
     */
    @PostMapping("/new")
    @Operation(hidden = true)
    public Boolean addUserRole(@RequestBody UserRole userRole) {
        return roleService.addUserRole(userRole);
    }

    /**
     * 更新用户权限信息
     * @param userRole
     * @return
     */
    @PutMapping("/update")
    @Operation(hidden = true)
    public Boolean updateUserRole(@RequestBody UserRole userRole) {
        return roleService.updateUserRole(userRole);
    }

    @DeleteMapping("/userId/{userId}/roleId/{roleId}")
    @Operation(hidden = true)
    public Boolean removeUserRole(@PathVariable("userId") String userId, @PathVariable("roleId") Long roleId) {
        return roleService.removeUserRole(userId, roleId);
    }
}
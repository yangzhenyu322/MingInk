package com.mingink.system.service.impl;

import com.mingink.system.api.domain.Role;
import com.mingink.system.domain.entity.UserRole;
import com.mingink.system.mapper.RoleMapper;
import com.mingink.system.mapper.UserRoleMapper;
import com.mingink.system.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Role服务业务
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:13
 */
@Slf4j
@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> getRolesByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectByMap(map);
        // 该用户的所有roleId
        List<Long> roleIds = userRoles.stream().map(userRole -> {
            return userRole.getRoleId();
        }).collect(Collectors.toList());

        // 将roleId转换为Role
        List<Role> roles = roleMapper.selectList(null);
        roles = roles.stream()
                .filter(role -> roleIds.contains(role.getRoleId()))
                .collect(Collectors.toList());

        return roles;
    }

    @Override
    public Boolean addUserRole(String userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        return userRoleMapper.insert(userRole) > 0;
    }
}
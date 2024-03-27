package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mingink.system.api.domain.entiry.Role;
import com.mingink.system.api.domain.entiry.UserRole;
import com.mingink.system.mapper.RoleMapper;
import com.mingink.system.mapper.UserRoleMapper;
import com.mingink.system.service.IRoleService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        LocalDateTime nowTime = LocalDateTime.now();
        List<Long> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            if (userRole.getIsDurable() == 1 && nowTime.isAfter(userRole.getExpirationTime())) {
                // 拥有的权限已过期
                continue;
            }
            roleIds.add(userRole.getRoleId());
        }

        // 将roleId转换为Role
        List<Role> roles = roleMapper.selectList(null);
        roles = roles.stream()
                .filter(role -> roleIds.contains(role.getRoleId()))
                .collect(Collectors.toList());

        return roles;
    }

    @Override
    public UserRole getUserRoleByIds(String userId, Long roleId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("role_id", roleId);
        return userRoleMapper.selectOne(queryWrapper);
    }

    @Override
    @GlobalTransactional
    public Boolean addUserRole(UserRole userRole) {
        return userRoleMapper.insert(userRole) > 0;
    }

    @Override
    public Boolean updateUserRole(UserRole userRole) {
        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userRole.getUserId());
        updateWrapper.eq("role_id", userRole.getRoleId());

        return userRoleMapper.update(userRole, updateWrapper) > 0;
    }

    @Override
    @GlobalTransactional
    public Boolean removeUserRoleByUserId(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return userRoleMapper.delete(queryWrapper) > 0;
    }

    @Override
    public Boolean removeUserRole(String userId, Long roleId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("role_id", roleId);
        return userRoleMapper.delete(queryWrapper) > 0;
    }
}
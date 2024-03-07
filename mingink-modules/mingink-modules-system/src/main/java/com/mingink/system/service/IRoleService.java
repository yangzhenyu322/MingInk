package com.mingink.system.service;

import com.mingink.system.api.domain.entiry.Role;

import java.util.List;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:13
 */
public interface IRoleService {
    List<Role> getRolesByUserId(String userId);

    Boolean addUserRole(String userId, Long roleId);

    Boolean removeUserRoleByUserId(String userId);
}

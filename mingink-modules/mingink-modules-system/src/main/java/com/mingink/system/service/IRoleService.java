package com.mingink.system.service;

import com.mingink.system.api.domain.entiry.Role;
import com.mingink.system.api.domain.entiry.UserRole;

import java.util.List;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:13
 */
public interface IRoleService {
    List<Role> getRolesByUserId(String userId);

    UserRole getUserRoleByIds(String userId, Long roleId);

    Boolean addUserRole(UserRole userRole);

    Boolean updateUserRole(UserRole userRole);

    Boolean removeUserRoleByUserId(String userId);

    Boolean removeUserRole(String userId, Long roleId);
}

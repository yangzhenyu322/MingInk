package com.mingink.system.service.impl;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.Role;
import com.mingink.system.mapper.RoleMapper;
import com.mingink.system.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

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

    @Override
    public Role getSysRoleById(Long id) {
        return roleMapper.selectById(id);
    }
}

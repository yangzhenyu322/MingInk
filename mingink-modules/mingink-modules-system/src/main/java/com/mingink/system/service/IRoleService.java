package com.mingink.system.service;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 19:13
 */
public interface IRoleService {
    Role getRoleById(Long id);
}

package com.mingink.system.service;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.User;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 12:01
 */
public interface IUserService {

    R<?> getUserList();

    User getUserByUserName(String username);

    R<?> changeUserPassword(User user);

    R<?> registerUser(User user);
}
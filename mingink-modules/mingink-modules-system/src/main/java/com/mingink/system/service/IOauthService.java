package com.mingink.system.service;

import com.mingink.system.api.domain.User;

/**
 * @Author: ZXY
 * @Date: 2024/2/25 12:01
 */
public interface IOauthService {
    /**
     * 通过第三方ID查询用户信息
     */
    User getUserByOAuthId(String oAuthId);


    /**
     * 创建用户
     */
    int createUser(User user);

    int updateUser(User user);
}
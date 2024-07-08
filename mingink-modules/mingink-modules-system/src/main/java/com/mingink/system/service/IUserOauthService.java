package com.mingink.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.system.api.domain.entity.UserOauth;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/7 10:39
 */
public interface IUserOauthService extends IService<UserOauth> {

    Boolean removeUserOauthByUserId(String userId);
}
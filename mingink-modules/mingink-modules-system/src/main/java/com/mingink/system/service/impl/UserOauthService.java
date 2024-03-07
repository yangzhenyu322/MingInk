package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.system.api.domain.entiry.UserOauth;
import com.mingink.system.mapper.UserOauthMapper;
import com.mingink.system.service.IUserOauthService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/7 10:40
 */
@Service
public class UserOauthService extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {
    @Autowired
    private UserOauthMapper userOauthMapper;

    @Override
    @GlobalTransactional
    public Boolean removeUserOauthByUserId(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return userOauthMapper.delete(queryWrapper) > 0;
    }
}

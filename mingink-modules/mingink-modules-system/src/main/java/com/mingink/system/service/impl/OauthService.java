package com.mingink.system.service.impl;

import com.mingink.system.api.domain.User;
import com.mingink.system.mapper.UserMapper;
import com.mingink.system.service.IOauthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OAuth服务业务
 * @Author: ZXY
 * @Date: 2024/2/25 12:01
 */
@Slf4j
@Service
public class OauthService implements IOauthService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByOAuthId(String oAuthId){
        if(StringUtils.isBlank(oAuthId)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oauth_id", oAuthId);
        List<User> users = userMapper.selectByMap(map);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public int createUser(User user) {

        return userMapper.insert(user);
    }
    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

}

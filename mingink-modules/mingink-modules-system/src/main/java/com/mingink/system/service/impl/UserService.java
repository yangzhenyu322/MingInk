package com.mingink.system.service.impl;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.User;
import com.mingink.system.mapper.UserMapper;
import com.mingink.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务业务
 * @Author: ZenSheep
 * @Date: 2024/2/1 12:02
 */
@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public R<?> getUserList() {
        List<User> userList = userMapper.selectList(null);

        if (userList.size() <= 0) {
            return R.fail("不存在任何用户");
        }

        return R.ok(userList);
    }
}

package com.mingink.system.service.impl;

import com.mingink.common.core.domain.R;
import com.mingink.common.core.utils.id.SnowFlakeFactory;
import com.mingink.system.api.domain.User;
import com.mingink.system.mapper.UserMapper;
import com.mingink.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public User getUserByUserName(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", username);
        List<User> sysUsers = userMapper.selectByMap(map);

        User user = null;
        if (sysUsers.size() > 0) {
            user = sysUsers.get(0);
        }

        return user;
    }

    @Override
    public R<?> changeUserPassword(User user) {
        String password = user.getPassword();
        String repeatPassword = user.getRepeatPassword();

        if (!password.equals(repeatPassword)) {
            return R.fail("密码不一致，请重新输入");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("user_name", user.getUserName());
        user.setUserId(userMapper.selectByMap(map).get(0).getUserId());
        boolean isUpdatedSuccess = userMapper.updateById(user) > 0;

        if(isUpdatedSuccess) {
            log.info("更新用户【{}】密码成功", user.getUserName());
            return R.ok("更新密码成功");
        }

        log.info("更新密码失败");
        return R.fail("更新密码失败");
    }

    @Override
    public R<?> registerUser(User user) {
        log.info("Begin Register: 【{}】", user.getUserName());

        // 检测用户名是否为空
        if (StringUtils.isEmpty(user.getUserName())) {
            return R.fail("用户名不能为空");
        }

        // 检测用户名是否重复
        Map<String, Object> userNameMap = new HashMap<>();
        userNameMap.put("user_name", user.getUserName());
        boolean isUserNameExit = userMapper.selectByMap(userNameMap).size() > 0;

        if (isUserNameExit) {
            return R.fail("该用户名已被注册");
        }

        // 密码格式检验
        if (StringUtils.isEmpty(user.getPassword())) {
            // 密码为空
            return R.fail("密码不能为空");
        }

        // 检测手机号格式是否正确
        if (user.getPhoneNumber().length() == 0) {
            return R.fail("手机号不能为空");
        }

        if (user.getPhoneNumber().length() != 11) {
            return R.fail("手机号长度不正确");
        }

        if (!StringUtils.isNumeric(user.getPhoneNumber())) {
            return R.fail("手机号格式不正确");
        }

        // 检测手机号是否被注册过
        Map<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("phone_number", user.getPhoneNumber());
        boolean isPhoneNumberExit = userMapper.selectByMap(phoneMap).size() > 0;

        if (isPhoneNumberExit) {
            return R.fail("该手机号已注册账户");
        }

        // 插入新用户
        user.setUserId(SnowFlakeFactory.getSnowFlakeFromCache().nextId()); // 雪花算法设置用户Id
        user.setUid((String.valueOf(userMapper.selectList(null).size() + 100001))); // 设置用户Uid
        user.setRoleId(3L);  // 设置默认权限
        user.setNickName(user.getUserName()); // 默认新用户昵称为用户（账户）名
        user.setAvatar("null"); // 设置用户默认头像
        user.setStatus(0); // 默认用户状态为正常——0
        user.setLoginDate(new Date()); // 最近登录时间
        user.setCreateTime(new Date()); // 当前时间
        user.setUpdateTime(new Date()); // 当前时间
        boolean isInsertSuccess = userMapper.insert(user) > 0;  // 返回值int代表插入成功条数，大于0表示插入成功条数，等于0则代表插入失败

        if (!isInsertSuccess) {
            log.info("用户[{}]注册失败：", user.getUserName());
            return R.fail("用户注册失败");
        }

        log.info("用户[{}]注册成功：", user.getUserName());
        return R.ok(null, "用户注册成功");
    }
}

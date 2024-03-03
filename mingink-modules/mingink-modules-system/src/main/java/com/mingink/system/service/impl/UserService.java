package com.mingink.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mingink.article.api.RemoteGorseService;
import com.mingink.article.api.domain.dto.GorseUserRequest;
import com.mingink.common.core.domain.R;
import com.mingink.common.core.utils.id.SnowFlakeFactory;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.system.api.domain.entiry.User;
import com.mingink.system.api.domain.vo.UserSafeInfo;
import com.mingink.system.api.domain.dto.UserInfoUptReq;
import com.mingink.system.mapper.UserMapper;
import com.mingink.system.service.IUserService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private RemoteGorseService remoteGorseService;

    @Override
    public R<List<UserSafeInfo>> getUserList() {
        List<User> userList = userMapper.selectList(null);

        if (userList.size() <= 0) {
            return R.fail("不存在任何用户");
        }
        List<UserSafeInfo> userSafeList = userList.stream().map(this::getSafeUser).collect(Collectors.toList());
        return R.ok(userSafeList);
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
    @GlobalTransactional
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
        user.setNickName(user.getUserName()); // 默认新用户昵称为用户（账户）名
        user.setAvatar("null"); // 设置用户默认头像
        user.setBirthday(new Date());
        user.setStatus(0); // 默认用户状态为正常——0
        user.setLoginDate(new Date()); // 最近登录时间
        user.setCreateTime(new Date()); // 当前时间
        user.setUpdateTime(new Date()); // 当前时间
        boolean isInsertSuccess = userMapper.insert(user) > 0;  // 返回值int代表插入成功条数，大于0表示插入成功条数，等于0则代表插入失败

        if (!isInsertSuccess) {
            log.info("用户[{}]注册失败：", user.getUserName());
            return R.fail("用户注册失败");
        }

        // 设置默认权限
        roleService.addUserRole(user.getUserId(), 3L);

        // 注册Gorse User
        GorseUserRequest gorseUserRequest = new GorseUserRequest();
        gorseUserRequest.setUserId(user.getUserId());
        gorseUserRequest.setLabels("[]");
        if (!remoteGorseService.addNewGorseUser(gorseUserRequest)) {
            // 注册Gorse User失败
            log.info("用户[{}]注册Gorse User失败：", user.getUserName());
            return R.fail("用户注册失败");
        }

        log.info("用户[{}]注册成功：", user.getUserName());
        return R.ok(null, "用户注册成功");
    }

    @Override
    public R<Boolean> updateUserInfo(UserInfoUptReq userInfo, UserSafeInfo loginUser) {
        String userId = userInfo.getUserId();
        if (StringUtils.isBlank(userId)) {
            return R.fail("用户信息异常");
        }
        //仅管理员和自己可以修改
        //管理员 允许更新任意用户
        //非管理员 只允许更新当前(自己的)信息
        if (!isAdmin(loginUser) && userId.equals(loginUser.getUserId())) {
            return R.fail("用户无权限");
        }
        User oldUser = userMapper.selectById(userInfo.getUserId());
        if (oldUser == null) {
            return R.fail("用户不存在");
        }
        oldUser.setNickName(userInfo.getNickName());
        oldUser.setAvatar(userInfo.getAvatar());
        oldUser.setEmail(userInfo.getEmail());
        oldUser.setRemark(userInfo.getRemark());
        oldUser.setCountry(userInfo.getCountry());
        oldUser.setAddress(userInfo.getAddress());
        oldUser.setRegion(userInfo.getRegion());
        int result = userMapper.updateById(oldUser);
        if(result != 0) {
            return R.ok(true, "用户信息更新成功");
        }
        return R.fail(false,"系统内部异常");
    }

    @Override
    public UserSafeInfo getCurrentUser(HttpServletRequest request) {
        //获取token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token: {}", token);
        if(StringUtils.isBlank(token)) {
            return null;
        }
        //解析token
        Map<String, Object> userMap = JWTUtils.getTokenInfo(token);
        String username = (String) userMap.get("username");
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        return getSafeUser(user);
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        //获取token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token: {}", token);
        if(StringUtils.isBlank(token)) {
            return false;
        }
        //解析token
        Map<String, Object> userMap = JWTUtils.getTokenInfo(token);
        List<String> roleKeys = JSON.parseArray((String) userMap.get("roleKeys"), String.class);
        return roleKeys.contains("admin");
    }

    @Override
    public boolean isAdmin(UserSafeInfo loginUser) {
        if (loginUser == null) {
            return false;
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", loginUser.getUserName());
        User user = userMapper.selectOne(queryWrapper);
        List<String> roleKeys = roleService.getRolesByUserId(user.getUserId()).stream().map(role -> {
            return role.getRoleKey();
        }).collect(Collectors.toList());

        return roleKeys.contains("admin");
    }

    @Override
    public R<List<UserSafeInfo>> searchUserByName(String username) {
        if (StringUtils.isBlank(username)) {
            return R.fail("用户名为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(users.size() == 0) {
            return R.fail("用户查询失败");
        }
        List<UserSafeInfo> results = users.stream().map(this::getSafeUser).collect(Collectors.toList());
        return R.ok(results, "用户查询成功");
    }

    @Override
    public User getUserByName(String username) {
        if(StringUtils.isBlank(username)) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("user_name", username);
        List<User> users = userMapper.selectByMap(map);
        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    @Override
    @GlobalTransactional
    public boolean removeUser(String userId) {
        // TODO 移除之前与其管理表的数据

        boolean isRemoveSucess = userMapper.deleteById(userId) > 0;

        if (isRemoveSucess) {
            if (remoteGorseService.removeGorseUser(userId)) {
                // 注销Gorse User成功
                log.info("注销Gorse User成功");
            } else {
                log.info("注销Gorse User失败");
            }
        }

        return isRemoveSucess;
    }

    /**
     * 获取脱敏信息
     * @param originUser
     * @return
     */
    UserSafeInfo getSafeUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        UserSafeInfo safeInfo = new UserSafeInfo();
        safeInfo.setBirthday(originUser.getBirthday());
        safeInfo.setEmail(originUser.getEmail());
        safeInfo.setRemark(originUser.getRemark());
        safeInfo.setSex(originUser.getSex());
        safeInfo.setUserName(originUser.getUserName());
        safeInfo.setAvatar(originUser.getAvatar());
        safeInfo.setUid(originUser.getUid());
        safeInfo.setUserId(originUser.getUserId());
        safeInfo.setPhoneNumber(originUser.getPhoneNumber());
        safeInfo.setNickName(originUser.getNickName());
        safeInfo.setAddress(originUser.getAddress());
        safeInfo.setCountry(originUser.getCountry());
        safeInfo.setRegion(originUser.getRegion());
        return safeInfo;
    }
}

package com.mingink.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mingink.article.api.RemoteAuthorService;
import com.mingink.article.api.RemoteGorseService;
import com.mingink.article.api.RemoteUserTagService;
import com.mingink.article.api.domain.dto.GorseUserRequest;
import com.mingink.common.core.domain.R;
import com.mingink.common.core.utils.id.SnowFlakeFactory;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.common.redis.service.RedisService;
import com.mingink.system.api.domain.dto.SmsLoginReq;
import com.mingink.system.api.domain.dto.UserInfoUptReq;
import com.mingink.system.api.domain.entity.Role;
import com.mingink.system.api.domain.entity.User;
import com.mingink.system.api.domain.entity.UserRole;
import com.mingink.system.api.domain.vo.UserSafeInfo;
import com.mingink.system.mapper.UserMapper;
import com.mingink.system.service.IOSSService;
import com.mingink.system.service.IUserOauthService;
import com.mingink.system.service.IUserService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户服务业务
 *
 * @Author: ZenSheep
 * @Date: 2024/2/1 12:02
 */
@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IOSSService oSSService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IUserOauthService userOauthService;

    @Autowired
    private RemoteGorseService remoteGorseService;

    @Autowired
    private RemoteUserTagService remoteUserTagService;

    @Autowired
    private RemoteAuthorService remoteAuthorService;

    @Autowired
    private RedisService redisService;

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
    public User getUserByUserId(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public Boolean updateUserAvatar(MultipartFile file, String userId) {
        String fileUrl = oSSService.uploadFile(file, userId + "/avatar/");
        User user = userMapper.selectById(userId);
        user.setAvatar(fileUrl);

        return userMapper.updateById(user) > 0;
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

        if (isUpdatedSuccess) {
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
        user.setAvatar("https://minio.lumoxuan.cn/mingink/public/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg"); // 设置用户默认头像
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
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(3L);
        userRole.setIsDurable(0); // 0表示永久权限
        roleService.addUserRole(userRole);

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
    @GlobalTransactional
    public R<String> verifyCodeToLogin(SmsLoginReq smsLoginReq) {
        String phoneNumber = smsLoginReq.getPhoneNumber();
        String requestId = smsLoginReq.getRequestId();
        String inputCode = smsLoginReq.getInputCode();
        String redisCode = redisService.getCacheObject(requestId);
        if (redisCode == null) {
            // 验证码过期
            log.error("[{}]验证失败:{}", requestId, "验证码已过期");
            return R.fail("验证码已过期");
        }
        if (!redisCode.equals(inputCode)) {
            // 验证码不正确
            log.error("[{}]验证失败:{}", requestId, "验证码有误");
            return R.fail("验证码有误");
        }

        log.info("[{}]验证成功", requestId);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phoneNumber);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            String userName = user.getUserName();
            List<String> roleKeys = roleService.getRolesByUserId(user.getUserId()).stream().map(Role::getRoleKey).collect(Collectors.toList());
            String roleKeysStr = JSON.toJSONString(roleKeys);
            Map<String, String> payload = new HashMap<>();
            payload.put("username", userName);
            payload.put("roleKeys", roleKeysStr);
            String token = JWTUtils.creatToken(payload, 60 * 60 * 24 * 1);
            redisService.setCacheObject(userName, token, 1, TimeUnit.DAYS);
            return R.ok(token);
        }
        User newUser = new User();
        String userId = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
        newUser.setUserId(userId); // 雪花算法设置用户Id
        String uid = String.valueOf(userMapper.selectList(null).size() + 100001);
        newUser.setUid(uid); // 设置用户Uid
        StringBuilder username = new StringBuilder("yingying" + generateRandomString(9));
        Map<String, Object> usernameMap = new HashMap<>();
        usernameMap.put("user_name", username.toString());
        while (userMapper.selectByMap(usernameMap).size() > 0) {
            username = new StringBuilder("yingying" + generateRandomString(9));
            usernameMap.put("user_name", username.toString());
        }
        newUser.setUserName(username.toString());
        newUser.setPhoneNumber(phoneNumber);
        newUser.setLoginDate(new Date());
        newUser.setNickName(username.toString());
        newUser.setAvatar("https://minio.lumoxuan.cn/mingink/public/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg"); // 设置用户默认头像
        newUser.setStatus(0); // 默认用户状态为正常——0
        boolean isInsertSuccess = userMapper.insert(newUser) > 0;  // 返回值int代表插入成功条数，大于0表示插入成功条数，等于0则代表插入失败

        if (!isInsertSuccess) {
            log.info("用户[{}]注册失败：", uid);
            return R.fail("用户注册失败");
        }
        // 设置默认权限
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(3L);
        userRole.setIsDurable(0); // 0表示永久权限
        roleService.addUserRole(userRole);

        // 注册Gorse User
        GorseUserRequest gorseUserRequest = new GorseUserRequest();
        gorseUserRequest.setUserId(userId);
        gorseUserRequest.setLabels("[]");
        if (!remoteGorseService.addNewGorseUser(gorseUserRequest)) {
            // 注册Gorse User失败
            log.info("用户[{}]注册Gorse User失败：", uid);
            return R.fail("用户注册失败");
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("username", username.toString());
        payload.put("roleKeys", "common");
        String token = JWTUtils.creatToken(payload, 60 * 60 * 24 * 1);
        redisService.setCacheObject(username.toString(), token, 1, TimeUnit.DAYS);
        return R.ok(token);
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
        if (!isAdmin(loginUser) && !userId.equals(loginUser.getUserId())) {
            return R.fail("用户无权限");
        }
        User oldUser = userMapper.selectById(userInfo.getUserId());
        if (oldUser == null) {
            return R.fail("用户不存在");
        }
        oldUser.setNickName(userInfo.getNickName());
        oldUser.setEmail(userInfo.getEmail());
        oldUser.setRemark(userInfo.getRemark());
        oldUser.setCountry(userInfo.getCountry());
        oldUser.setAddress(userInfo.getAddress());
        oldUser.setRegion(userInfo.getRegion());
        int result = userMapper.updateById(oldUser);
        if (result != 0) {
            return R.ok(true, "用户信息更新成功");
        }
        return R.fail(false, "系统内部异常");
    }

    @Override
    public UserSafeInfo getCurrentUser(HttpServletRequest request) {
        //获取token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token: {}", token);
        if (StringUtils.isBlank(token)) {
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
        if (StringUtils.isBlank(token)) {
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
        if (users.size() == 0) {
            return R.fail("用户查询失败");
        }
        List<UserSafeInfo> results = users.stream().map(this::getSafeUser).collect(Collectors.toList());
        return R.ok(results, "用户查询成功");
    }

    @Override
    public User getUserByName(String username) {
        if (StringUtils.isBlank(username)) {
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
    public Boolean removeUser(String userId) {
        // 删除User Tag
        remoteUserTagService.removeUserTagByUserId(userId);

        // 删除User OAuth
        userOauthService.removeUserOauthByUserId(userId);

        // 删除User Role
        roleService.removeUserRoleByUserId(userId);

        // 删除Gorse Feedback
        remoteGorseService.removeFeedBackByUserId(userId);

        // 删除Gorse User
        remoteGorseService.removeGorseUser(userId);

        // 删除Author
        remoteAuthorService.removeAuthorByUserId(userId);

        boolean isRemoveSucess = userMapper.deleteById(userId) > 0;

        return isRemoveSucess;
    }

    /**
     * 获取脱敏信息
     *
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

    String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
}

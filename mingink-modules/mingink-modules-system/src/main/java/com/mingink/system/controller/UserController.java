package com.mingink.system.controller;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.UserInfoUptReq;
import com.mingink.system.api.domain.entiry.User;
import com.mingink.system.api.domain.vo.UserSafeInfo;
import com.mingink.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务接口
 * @Author: ZenSheep
 * @Date: 2024/2/1 12:03
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户接口功能", tags = "UserController", description = "用户接口相关介绍")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/vip")
    public R<String> getVIP() {
        return R.ok("vip");
    }

    @GetMapping("/admin")
    public R<String> getAdmin() {
        return R.ok("admin");
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("查询所有用户信息")
    public R<List<UserSafeInfo>> getAllUsers() {
        return userService.getUserList();
    }

    /**
     * 通过用户名模糊查询多个用户
     * @param username
     * @return
     */
    @GetMapping("/userSafeInfos/username/{username}")
    @ApiOperation("通过用户名模糊查询多个用户信息")
    public R<List<UserSafeInfo>> getUserSafeInfoListByUserName(@PathVariable("username") String username) {
        return userService.searchUserByName(username);
    }

    /**
     * 通过用户名查询单个用户
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    public User getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByName(username);
    }

    /**
     * 忘记密码（修改密码）
     * @param user
     * @return
     */
    @PutMapping("/password")
    @ApiOperation("忘记密码（修改密码）")
    public R<?> changeUserPassword(@RequestBody User user) {
        return userService.changeUserPassword(user);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public R<?> register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/current")
    @ApiOperation("个人中心获取当前登录用户信息")
    public R<UserSafeInfo> getCurrentUser(HttpServletRequest request) {
        return R.ok(userService.getCurrentUser(request));
    }

    @PutMapping("/updateInfo")
    @ApiOperation("更新用户基础信息")
    public R<Boolean> updateUser(@RequestBody UserInfoUptReq userInfo, HttpServletRequest request) {
        UserSafeInfo loginUser = userService.getCurrentUser(request);
        return userService.updateUserInfo(userInfo, loginUser);
    }

    /**
     * 注销用户
     * @return
     */
    @DeleteMapping("/userId/{userId}")
//    @ApiOperation("注销用户")
    public R<String> removeUser(@PathVariable("userId") String userId) {
        if (userService.removeUser(userId)) {
            log.info("用户[{}]注销失败", userId);
            return R.fail("用户注销失败");
        }

        log.info("用户[{}]注销成功", userId);
        return R.ok("用户注销成功");
    }
}
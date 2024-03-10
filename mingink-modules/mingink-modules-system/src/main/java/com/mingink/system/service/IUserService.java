package com.mingink.system.service;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.UserInfoUptReq;
import com.mingink.system.api.domain.entiry.User;
import com.mingink.system.api.domain.vo.UserSafeInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 12:01
 */
public interface IUserService {

    R<List<UserSafeInfo>> getUserList();

    R<?> changeUserPassword(User user);

    R<?> registerUser(User user);

    Boolean updateUserAvatar(MultipartFile file, String userId);

    R<Boolean> updateUserInfo(UserInfoUptReq userInfo, UserSafeInfo loginUser);

    /**
     * 获取当前登录用户
     * @param request
     */
    UserSafeInfo getCurrentUser(HttpServletRequest request);

    /**
     * 判断是否管理员
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 判断是否管理员
     */
    boolean isAdmin(UserSafeInfo loginUser);

    /**
     * 通过用户名查询用户信息
     */
    R<List<UserSafeInfo>> searchUserByName(String username);

    User getUserByName(String username);

    Boolean removeUser(String userId);

    User getUserByUserId(String userId);
}
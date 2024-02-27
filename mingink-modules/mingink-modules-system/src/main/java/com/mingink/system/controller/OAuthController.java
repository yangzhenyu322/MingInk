package com.mingink.system.controller;

import com.mingink.system.api.domain.User;
import com.mingink.system.service.IOauthService;
import com.mingink.system.service.IUserService;
import io.swagger.annotations.Api;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oAuth")
@Api(value = "第三方登录功能", tags = "oAuthController", description = "第三方登录相关介绍")
public class OAuthController {

    @Autowired
    private IOauthService oauthService;

    @Autowired
    private IUserService userService;

    /**
     * QQ登录request类
     * @return 链接
     * @Author: ZXY
     * @Date: 2024/2/25 15:03
     */
    private AuthQqRequest getQQAuthRequest() {
        return new AuthQqRequest(AuthConfig.builder()
                .clientId("appid等待qq审核")
                .clientSecret("appsecret等待QQ审核")
                .redirectUri("http://223.82.75.76:8848/plugin/qqlogin/callback")
                .unionId(true)
                .build());
    }

    /**
     * 跳转至登录页面
     * @Author: ZXY
     * @Date: 2024/2/25 15:03
     * @param response 页面跳转
     */
    @RequestMapping("/qqlogin")
    public void qqlogin(HttpServletResponse response) throws IOException {
        //获取qq实例
        AuthQqRequest authRequest = getQQAuthRequest();
        //打印生成的链接
        System.out.println("生成登录链接：" + authRequest.authorize("yourState"));
        System.out.println();
        //页面跳转，其中state参数可自定义
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    /**
     * QQ登录成功后返回到此页
     * @param callback 登录用户的信息
     * @return
     */
    @RequestMapping("/qqlogin/callback")
    public Object qqloginCallback(AuthCallback callback) {
        //获取实例
        AuthQqRequest authRequest = getQQAuthRequest();

        // 获取第三方登录用户信息
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        AuthUser authUser = authResponse.getData();

        //不同情况判断进行怎样的操作
        if (authResponse.ok()) {
            // 第三方登录成功
            // 根据第三方登录账号ID查询用户是否存在
            // 验证用户是否已注册,从前端获取用户名
            User user = oauthService.getUserByOAuthId(authUser.getUuid()); //

            if (user == null) {  // 未注册的第三方登录
                // 返回qq信息给前端新增oauth_type、oauth_id，前端让用户补充相关信息
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("oauthType", 2); // 第三方登录类型
                responseData.put("oauthId", authUser.getUuid()); // 第三方登录账号ID
                responseData.put("nickName", authUser.getUsername()); // 别称
                responseData.put("avatar", authUser.getAvatar());//头像
                responseData.put("email", authUser.getEmail() );//邮箱
                //如有其他需要返回的信息可以进行补充
                return responseData;

            } else {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("message", "登录成功");
                responseData.put("code", authResponse.getCode());
                responseData.put("token", authUser.getToken());
                return responseData;
            }

        } else {
            /// 第三方登录失败，返回状态码给前端
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("message", "第三方登录失败：" + authResponse.getCode());
            return errorResponse;
        }
    }
/**
 * 微信登录request类
 * @return 链接
 * @Author: ZXY
 * @Date: 2024/2/25 15:03
 */


}


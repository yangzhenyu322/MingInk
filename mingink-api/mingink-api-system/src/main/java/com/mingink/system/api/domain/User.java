package com.mingink.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户实体类
 * @author ZenSheep
 */
@Data
@TableName("user")
public class User implements Serializable {
    /**
    * 用户ID
    */
    @TableId
    private String userId;

    /**
     * uid（100001开始）
     */
    private String uid;

    /**
     * 用户名称（账号）
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
    * 用户头像（有默认头像）
    */
    private String avatar;

    /**
    * 用户性别（0：男，1:女，2：未知，默认为未知）
    */
    private Integer sex;
    /**
    * 生日
    */
    private Date birthday;

    /**
    * 用户权限 (1: 管理员 2:VIP用户 3: 普通用户)
    * 默认普通成员
    */
    private Integer roleId;

    /**
    * 个性签名
    */
    private String remark;

    /**
    * 用户标签
    */
    private String tag;

    /**
    * 电话号码
    */
    private String phoneNumber;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 账户状态 0正常 1停用 2注销
    * 默认正常
    */
    private Integer status;

    /**
    * 用户IP
    */
    private String loginIp;

    /**
    * 用户最后登录时间
    */
    private Date loginDate;

    /**
    * 第三方登录类型（1：微信，2：QQ）等等
    */
    private Integer oauthType;

    /**
    * 第三方登录账号ID
    */
    private String oauthId;

    /**
    * 认证access_token
    */
    private String accessToken;

    /**
    * 认证refresh_token
    */
    private String refreshToken;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}
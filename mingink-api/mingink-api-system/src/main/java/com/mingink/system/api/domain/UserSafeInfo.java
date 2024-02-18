package com.mingink.system.api.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/18 10:46
 * @description 脱敏用户信息
 */
@Data
public class UserSafeInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -8933150503249502227L;
    /**
     * 用户ID
     */
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
     * 国家
     */
    private String country;

    /**
     * 所在地区
     */
    private String region;

    /**
     * 具体地址
     */
    private String address;

}

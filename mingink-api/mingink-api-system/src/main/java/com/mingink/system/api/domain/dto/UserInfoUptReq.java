package com.mingink.system.api.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/18 10:36
 * @description 用户信息修改请求实体
 */
@Data
public class UserInfoUptReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 6913991521842271421L;
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
     * 个人简介
     */
    private String remark;

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

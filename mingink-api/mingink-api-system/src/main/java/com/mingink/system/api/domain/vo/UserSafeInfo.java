package com.mingink.system.api.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "用户安全信息")
public class UserSafeInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -8933150503249502227L;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * uid（100001开始）
     */
    @Schema(description = "uid（100001开始）")
    private String uid;

    /**
     * 用户名称（账号）
     */
    @Schema(description = "用户名称（账号）")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 用户头像（有默认头像）
     */
    @Schema(description = "用户头像（有默认头像）")
    private String avatar;

    /**
     * 用户性别（0：男，1:女，2：未知，默认为未知）
     */
    @Schema(description = "用户性别（0：男，1:女，2：未知，默认为未知）")
    private Integer sex;
    /**
     * 生日
     */
    @Schema(description = "生日")
    private Date birthday;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    private String remark;

    /**
     * 电话号码
     */
    @Schema(description = "电话号码")
    private String phoneNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 国家
     */
    @Schema(description = "国家")
    private String country;

    /**
     * 所在地区
     */
    @Schema(description = "所在地区")
    private String region;

    /**
     * 具体地址
     */
    @Schema(description = "具体地址")
    private String address;
}

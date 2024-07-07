package com.mingink.system.api.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户实体类
 * @author ZenSheep
 *
 */
@Data
@TableName("user")
@Schema(description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 用户ID
    */
    @TableId
    @Schema(description = "用户ID")
    private String userId;

    /**
     * uid（100001开始）
     */
    @Schema(description = "用户uid（100001开始）")
    private String uid;

    /**
     * 用户名称（账号）
     */
    @Schema(description = "用户名称（账号）")
    private String userName;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

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

    /**
    * 账户状态 0正常 1停用 2注销
    * 默认正常
    */
    @Schema(description = "账户状态 0正常 1停用 2注销")
    private Integer status;

    /**
    * 用户登录IP
    */
    @Schema(description = "用户登录IP")
    private String loginIp;

    /**
    * 用户最后登录时间
    */
    @Schema(description = "用户最后登录时间")
    private Date loginDate;

    /**
    * 创建时间
    */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
    * 更新时间
    */
    @Schema(description = "更新时间")
    private Date updateTime;

    // 非表字段

    /**
     * 重复密码
     */
    @TableField(exist = false)
    @Schema(description = "重复密码")
    private String repeatPassword;

    /**
     * 证码请求id验证码请求id
     */
    @TableField(exist = false)
    @Schema(description = "证码请求ID")
    private String codeRequestId;

    /**
     * 输入的验证码
     */
    @TableField(exist = false)
    @Schema(description = "输入的验证码")
    private String inputCode;
}
package com.mingink.system.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZenSheep
 * @since 2024-03-07
 */
@Data
@TableName("user_oauth")
@Schema(description = "用户第三方授权")
public class UserOauth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.NONE)
    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "授权类型")
    private Long oauthId;

    @Schema(description = "第三方平台的用户UID")
    private String uuid;

    @Schema(description = "第三方平台的用户账号")
    private String account;
}

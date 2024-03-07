package com.mingink.system.api.domain.entiry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZenSheep
 * @since 2024-03-07
 */
@Data
@TableName("user_oauth")
@ApiModel(value="UserOauth对象", description="UserOauth实体对象")
public class UserOauth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.NONE)
    private String userId;

    @ApiModelProperty(value = "授权类型")
    private Long oauthId;

    @ApiModelProperty(value = "第三方平台的用户UID")
    private String uuid;

    @ApiModelProperty(value = "第三方平台的用户账号")
    private String account;
}

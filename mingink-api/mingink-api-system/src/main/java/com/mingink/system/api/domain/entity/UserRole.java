package com.mingink.system.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联实体
 * @Author: ZenSheep
 * @Date: 2024/2/23 13:33
 */
@Data
@TableName("user_role")
@Schema(description = "用户角色关联实体")
public class UserRole {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.NONE)
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "是否永久，0-永久，1-非永久")
    private Integer isDurable;

    @Schema(description = "权限过期时间")
    private LocalDateTime expirationTime;
}
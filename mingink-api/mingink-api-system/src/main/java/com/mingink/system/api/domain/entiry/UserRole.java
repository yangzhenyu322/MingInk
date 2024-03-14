package com.mingink.system.api.domain.entiry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
}
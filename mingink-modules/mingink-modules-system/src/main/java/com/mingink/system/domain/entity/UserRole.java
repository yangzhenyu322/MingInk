package com.mingink.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户角色关联实体
 * @Author: ZenSheep
 * @Date: 2024/2/23 13:33
 */
@Data
@TableName("user_role")
public class UserRole {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.NONE)
    private String userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;
}
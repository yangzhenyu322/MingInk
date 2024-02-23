package com.mingink.system.domain;

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
    private String userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
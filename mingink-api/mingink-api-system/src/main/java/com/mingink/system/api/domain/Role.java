package com.mingink.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * Role实体类
 * @author ZenSheep
 * 2024/2/1 18:37
 */
@Data
@TableName("role")
public class Role{
    /**
    * 用户角色ID
    */
    @TableId
    private Long roleId;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 角色权限字符串
    */
    private String roleKey;

    /**
    * 显示顺序
    */
    private Integer roleSort;

    /**
    * 数据范围
    */
    private Integer dataScope;

    /**
    * 角色状态
    */
    private Integer status;

    /**
    * 备注
    */
    private String remark;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}

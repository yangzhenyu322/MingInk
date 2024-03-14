package com.mingink.system.api.domain.entiry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * Role实体类
 * @author ZenSheep
 * 2024/2/1 18:37
 */
@Data
@TableName("role")
@Schema(description = "角色")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 用户角色ID
    */
    @TableId(value = "role_id", type = IdType.AUTO)
    @Schema(description = "用户角色ID")
    private Long roleId;

    /**
    * 角色名称
    */
    @Schema(description = "角色名称")
    private String roleName;

    /**
    * 角色权限字符串
    */
    @Schema(description = "角色权限字符串")
    private String roleKey;

    /**
    * 显示顺序
    */
    @Schema(description = "显示顺序")
    private Integer roleSort;

    /**
    * 数据范围
    */
    @Schema(description = "数据范围")
    private Integer dataScope;

    /**
    * 角色状态
    */
    @Schema(description = "角色状态")
    private Integer status;

    /**
    * 备注
    */
    @Schema(description = "备注")
    private String remark;

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
}

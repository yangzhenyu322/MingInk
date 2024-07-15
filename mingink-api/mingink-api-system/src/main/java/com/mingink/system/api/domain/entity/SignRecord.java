package com.mingink.system.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @TableName sign_record
 */
@TableName(value ="sign_record")
@Data
@Schema(description = "抽签记录")
public class SignRecord implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 用户名
     */
    @TableField("user_name")
    @Schema(description = "用户名")
    private String userName;

    /**
     * 签文ID
     */
    @TableField("sign_id")
    @Schema(description = "签文ID")
    private Long signId;

    /**
     * 记录状态
     */
    @TableField("status")
    @Schema(description = "记录状态")
    private Integer status;

    /**
     * 抽签时间
     */
    @TableField("create_time")
    @Schema(description = "抽签时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @Schema(description = "更新时间")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
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
 * @TableName sign_message
 */
@TableName(value ="sign_message")
@Data
public class SignMessage implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 签文内容
     */
    @TableField("content")
    @Schema(description = "签文内容")
    private String content;

    /**
     * 签文类型
     */
    @TableField("type")
    @Schema(description = "签文类型")
    private String type;

    /**
     * 签文解读
     */
    @TableField("description")
    @Schema(description = "签文解读")
    private String description;

    /**
     * 状态
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
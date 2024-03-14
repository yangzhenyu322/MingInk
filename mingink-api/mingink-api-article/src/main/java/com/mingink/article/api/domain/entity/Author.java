package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("author")
@Schema(description = "作者")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @Schema(description = "作家id")
    private String id;

    @TableField("user_id")
    @Schema(description = "用户id")
    private String userId;

    @TableField("pen_name")
    @Schema(description = "笔名")
    private String penName;

    @TableField("status")
    @Schema(description = "作家状态：0-正常、1-禁用")
    private int status;

    @TableField("create_time")
    @Schema(description = "注册时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "个人信息更新时间")
    private LocalDateTime updateTime;
}

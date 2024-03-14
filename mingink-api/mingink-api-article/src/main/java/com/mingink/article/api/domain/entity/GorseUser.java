package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * GorseUsers 用户实体类
 * @author ZenSheep
 * @since 2024-02-29
 */
@Data
@TableName("gorse_users")
@Schema(description = "Gorse用户对象")
public class GorseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.NONE)
    @Schema(description = "用户ID")
    private String userId;

    @TableField("labels")
    @Schema(description = "标签Key集合，例[\"玄幻\", \"现代\"]")
    private String labels;

    @TableField("comment")
    @Schema(description = "个人简介")
    private String comment;

    @TableField("subscribe")
    @Schema(description = "订阅")
    private String subscribe;
}
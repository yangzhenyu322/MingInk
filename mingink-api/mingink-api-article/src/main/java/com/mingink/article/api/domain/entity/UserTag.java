package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZenSheep
 * @since 2024-02-29
 */
@Data
@TableName("user_tag")
@Schema(description = "用户标签")
public class UserTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.NONE)
    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "标签ID")
    private Long tagId;
}

package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * BookTag 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("book_tag")
@Schema(description = "小说标签")
public class BookTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "book_id", type = IdType.NONE)
    @Schema(description = "小说ID")
    private Long bookId;

    @TableField("tag_id")
    @Schema(description = "标签ID")
    private Long tagId;
}
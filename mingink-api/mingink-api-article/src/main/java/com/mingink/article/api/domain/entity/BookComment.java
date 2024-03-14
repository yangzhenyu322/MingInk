package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Comment 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("book_comment")
@Schema(description = "小说评论")
public class BookComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "小说评论id")
    private Long id;

    @TableField("book_id")
    @Schema(description = "小说id")
    private Long bookId;

    @TableField("user_id")
    @Schema(description = "用户id（读者）")
    private String userId;

    @TableField("upper_level_id")
    @Schema(description = "上一级评论id")
    private Long upperLevelId;

    @TableField("content")
    @Schema(description = "评论内容")
    private String content;

    @TableField("like_count")
    @Schema(description = "点赞数")
    private Integer likeCount;

    @TableField("tread_count")
    @Schema(description = "点踩数")
    private Integer treadCount;

    @TableField("is_top")
    @Schema(description = "是否置顶：0-否、1-是")
    private int isTop;

    @TableField("is_essence")
    @Schema(description = "是否精华：0-否、1-是")
    private int isEssence;

    @TableField("create_time")
    @Schema(description = "评论时间")
    private LocalDateTime createTime;
}

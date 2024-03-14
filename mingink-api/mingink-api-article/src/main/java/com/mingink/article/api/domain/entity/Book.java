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
 * Book 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("book")
@Schema(description = "小说")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "小说id")
    private Long id;

    @TableField("author_id")
    @Schema(description = "作家id")
    private String authorId;

    @TableField("name")
    @Schema(description = "名称")
    private String name;

    @TableField("description")
    @Schema(description = "描述")
    private String description;

    @TableField("pic_url")
    @Schema(description = "封面图片url")
    private String picUrl;

    @TableField("status")
    @Schema(description = "状态：0-连载(默认)、1-已完结、2-下架")
    private int status;

    @TableField("read_count")
    @Schema(description = "阅读量")
    private Long readCount;

    @TableField("like_count")
    @Schema(description = "喜欢量")
    private Long likeCount;

    @TableField("star_count")
    @Schema(description = "收藏量")
    private Long starCount;

    @TableField("word_count")
    @Schema(description = "总字数(已发布)")
    private Integer wordCount;

    @TableField("comment_count")
    @Schema(description = "评论数")
    private Integer commentCount;

    @TableField("score")
    @Schema(description = "评分：总分10")
    private Float score;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}

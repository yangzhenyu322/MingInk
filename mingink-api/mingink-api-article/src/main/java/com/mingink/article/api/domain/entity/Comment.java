package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Comment 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
@ApiModel(value="Comment对象", description="Comment实体对象")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小说评论id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小说id")
    @TableField("book_id")
    private Long bookId;

    @ApiModelProperty(value = "用户id（读者）")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "上一级评论id")
    @TableField("upper_level_id")
    private Long upperLevelId;

    @ApiModelProperty(value = "评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "点赞数")
    @TableField("like_count")
    private Integer likeCount;

    @ApiModelProperty(value = "点踩数")
    @TableField("tread_count")
    private Integer treadCount;

    @ApiModelProperty(value = "是否置顶：0-否、1-是")
    @TableField("is_top")
    private int isTop;

    @ApiModelProperty(value = "是否精华：0-否、1-是")
    @TableField("is_essence")
    private int isEssence;

    @ApiModelProperty(value = "评论时间")
    @TableField("create_time")
    private LocalDateTime createTime;
}

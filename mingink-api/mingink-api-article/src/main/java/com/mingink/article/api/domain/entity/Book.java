package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Book对象", description="Book 小说实体对象")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小说id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "作家id")
    @TableField("author_id")
    private String authorId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "封面图片url")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty(value = "状态：0-连载(默认)、1-已完结、2-下架")
    @TableField("status")
    private int status;

    @ApiModelProperty(value = "阅读量")
    @TableField("read_count")
    private Long readCount;

    @ApiModelProperty(value = "喜欢量")
    @TableField("like_count")
    private Long likeCount;

    @ApiModelProperty(value = "收藏量")
    @TableField("star_count")
    private Long starCount;

    @ApiModelProperty(value = "总字数(已发布)")
    @TableField("word_count")
    private Integer wordCount;

    @ApiModelProperty(value = "评论数")
    @TableField("comment_count")
    private Integer commentCount;

    @ApiModelProperty(value = "评分：总分10")
    @TableField("score")
    private Float score;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}

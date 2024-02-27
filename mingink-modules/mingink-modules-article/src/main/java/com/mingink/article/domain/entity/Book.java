package com.mingink.article.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    @TableField("desc")
    private String desc;

    @ApiModelProperty(value = "封面图片url")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty(value = "状态：0-连载、1-已完结、2-下架")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty(value = "点击量")
    @TableField("visit_count")
    private Long visitCount;

    @ApiModelProperty(value = "收藏量")
    @TableField("collect_count")
    private Long collectCount;

    @ApiModelProperty(value = "总字数")
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

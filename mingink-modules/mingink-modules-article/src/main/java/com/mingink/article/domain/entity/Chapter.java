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
 * Chapter 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("chapter")
@ApiModel(value="Chapter对象", description="Chapter实体对象")
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小说章节id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小说id")
    @TableField("book_id")
    private Long bookId;

    @ApiModelProperty(value = "序号")
    @TableField("serial_number")
    private Integer serialNumber;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "总字数")
    @TableField("word_count")
    private Integer wordCount;

    @ApiModelProperty(value = "章节状态：0-创建（草稿）、1-发布、2-下架")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty(value = "是否VIP专属：0-否、1-是")
    @TableField("is_vip")
    private Boolean isVip;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;
}

package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mingink.common.core.handler.MyBatisTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Chapter 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("chapter")
@Schema(description = "小说章节")
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "小说章节id")
    private Long id;

    @TableField("book_id")
    @Schema(description = "小说id")
    private Long bookId;

    @TableField("serial_number")
    @Schema(description = "序号(序号越小说明创建时间越早，不一定是连续的)")
    private Integer serialNumber;

    @TableField("title")
    @Schema(description = "标题")
    private String title;

    @TableField(value = "content", typeHandler = MyBatisTypeHandler.class)
    @Schema(description = "内容")
    private String content;

    @TableField("word_count")
    @Schema(description = "总字数")
    private Integer wordCount;

    @TableField("status")
    @Schema(description = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private int status;

    @TableField("is_vip")
    @Schema(description = "是否VIP专属：0-否、1-是")
    private int isVip;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableField("publish_time")
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;
}

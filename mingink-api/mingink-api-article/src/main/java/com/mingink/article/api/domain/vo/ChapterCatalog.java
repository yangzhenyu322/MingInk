package com.mingink.article.api.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mingink.article.api.domain.entity.Chapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/5 10:44
 */
@Data
@ApiModel(value="章节目录对象", description="章节目录实体类")
public class ChapterCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小说章节id")
    private Long id;

    @ApiModelProperty(value = "序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "字数")
    private Integer wordCount;

    @ApiModelProperty(value = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private int status;

    @ApiModelProperty(value = "是否VIP专属：0-否、1-是")
    private int isVip;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;

    public static ChapterCatalog chapterToChapterCatalog(Chapter chapter) {
        ChapterCatalog chapterCatalog = new ChapterCatalog();
        chapterCatalog.setId(chapter.getId());
        chapterCatalog.setSerialNumber(chapter.getSerialNumber());
        chapterCatalog.setTitle(chapter.getTitle());
        chapterCatalog.setWordCount(chapter.getWordCount());
        chapterCatalog.setStatus(chapter.getStatus());
        chapterCatalog.setIsVip(chapterCatalog.getIsVip());
        chapterCatalog.setCreateTime(chapter.getCreateTime());
        chapterCatalog.setUpdateTime(chapter.getUpdateTime());
        chapterCatalog.setPublishTime(chapter.getPublishTime());
        return chapterCatalog;
    }
}

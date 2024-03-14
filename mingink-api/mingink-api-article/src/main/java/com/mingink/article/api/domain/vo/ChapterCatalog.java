package com.mingink.article.api.domain.vo;

import com.mingink.article.api.domain.entity.Chapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/5 10:44
 */
@Data
@Schema(description = "章节目录")
public class ChapterCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "小说章节id")
    private Long id;

    @Schema(description = "序号")
    private Integer serialNumber;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "字数")
    private Integer wordCount;

    @Schema(description = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private int status;

    @Schema(description = "是否VIP专属：0-否、1-是")
    private int isVip;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "发布时间")
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

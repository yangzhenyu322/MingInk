package com.mingink.article.api.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/5 11:09
 */
@Data
@Schema(description = "分页章节目录")
public class PageChapterCatalog {

    @Schema(description = "分页查询结果条数")
    private Long total;

    @Schema(description = "分页查询结果")
    private List<ChapterCatalog> chapterCatalogs;

    public PageChapterCatalog() {

    }

    public PageChapterCatalog(Long total, List<ChapterCatalog> chapterCatalogs) {
        this.total = total;
        this.chapterCatalogs = chapterCatalogs;
    }
}

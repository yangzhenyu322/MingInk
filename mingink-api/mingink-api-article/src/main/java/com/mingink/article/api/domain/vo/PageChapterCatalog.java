package com.mingink.article.api.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/5 11:09
 */
@Data
@ApiModel(value="分页章节目录对象", description="分页章节目录实体类")
public class PageChapterCatalog {
    @ApiModelProperty(value = "分页查询结果条数")
    private Long total;
    @ApiModelProperty(value = "分页查询结果")
    private List<ChapterCatalog> chapterCatalogs;

    public PageChapterCatalog() {

    }

    public PageChapterCatalog(Long total, List<ChapterCatalog> chapterCatalogs) {
        this.total = total;
        this.chapterCatalogs = chapterCatalogs;
    }
}

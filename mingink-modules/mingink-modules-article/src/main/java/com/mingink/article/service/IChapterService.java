package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.dto.ChapterRequest;
import com.mingink.article.api.domain.dto.PageChapterCatalogRequest;
import com.mingink.article.api.domain.entity.Chapter;
import com.mingink.article.api.domain.vo.ChapterCatalog;
import com.mingink.article.api.domain.vo.PageChapterCatalog;

import java.util.List;

/**
 * Chapter 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IChapterService extends IService<Chapter> {
    List<ChapterCatalog> getChapterCatalogsByBookIdAndStatus(Long bookId, Integer status);

    PageChapterCatalog getPageChapterCatalogsByBookId(PageChapterCatalogRequest pageChapterCatalogRequest);

    Chapter getChapterById(Long chapterId);

    Boolean addChapter(ChapterRequest chapterRequest);

    Boolean updateChapter(ChapterRequest chapterRequest, Long chapterId);


    Boolean updateChapterStatus(Long chapterId, Integer status);
}
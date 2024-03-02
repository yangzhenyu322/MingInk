package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.dto.BaseChapterRequest;
import com.mingink.article.api.domain.dto.AddChapterRequest;
import com.mingink.article.api.domain.entity.Chapter;
import com.mingink.common.core.domain.R;

/**
 * Chapter 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IChapterService extends IService<Chapter> {
    /**
     * 添加小说章节
     * @param addChapterRequest
     * @return
     */
    R<Boolean> addChapter(AddChapterRequest addChapterRequest);

    /**
     * 修改小说章节状态信息
     * @param baseChapterRequest
     * @return
     */
    R<Boolean> updateChapterStatus(BaseChapterRequest baseChapterRequest);

}
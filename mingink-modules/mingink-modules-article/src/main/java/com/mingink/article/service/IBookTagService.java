package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.entity.BookTag;

import java.util.List;

/**
 * Book 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IBookTagService extends IService<BookTag> {
    List<String> getTagNamesByBookId(Long bookId);

    String getTagNamesStrByBookId(Long bookId);
}

package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.entity.BookTag;
import com.mingink.article.api.domain.entity.Tag;

import java.util.List;

/**
 * Book 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IBookTagService extends IService<BookTag> {

    String getTagNamesStrByBookId(Long bookId);

    List<Tag> getBookTagsById(Long bookId);

    Boolean addNewBookTag(BookTag bookTag);

    Boolean removeBookTag(BookTag bookTag);
}

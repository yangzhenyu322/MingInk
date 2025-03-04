package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.entity.BookComment;

/**
 * Comment 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IBookCommentService extends IService<BookComment> {

    Boolean removeAllComment(Long bookId);
}

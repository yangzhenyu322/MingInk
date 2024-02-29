package com.mingink.article.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.entity.Book;
import com.mingink.article.api.domain.vo.BookRequest;

import java.util.List;

/**
 * Book 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IBookService extends IService<Book> {
    List<Book> searchBooksByName(String name);

    Boolean insertBook(BookRequest bookRequest);

    Boolean updateBookInfo(BookRequest bookRequest, Long bookId);

    Boolean updateBookStatus(Long bookId, int status);

    Boolean updateBookVisitCount(Long bookId);
}

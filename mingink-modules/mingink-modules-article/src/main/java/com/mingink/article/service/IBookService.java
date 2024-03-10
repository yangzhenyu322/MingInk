package com.mingink.article.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.dto.BookRequest;
import com.mingink.article.api.domain.dto.GorseFeedbackRequest;
import com.mingink.article.api.domain.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Book 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IBookService extends IService<Book> {
    List<Book> searchBooksByName(String name);

    String uploadPic(MultipartFile file, String userId);

    Boolean insertBook(BookRequest bookRequest);

    Boolean updateBookInfo(BookRequest bookRequest, Long bookId);

    boolean updateBookStatus(Long bookId, int status);

    Boolean addBookFeedback(GorseFeedbackRequest gorseFeedbackRequest);

    Boolean substrateBookFeedback(GorseFeedbackRequest gorseFeedbackRequest);

    Boolean removeBookById(Long bookId);
}
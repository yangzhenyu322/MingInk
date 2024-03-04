package com.mingink.article.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.dto.AuthorRequest;
import com.mingink.article.api.domain.entity.Author;

import java.util.List;

/**
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface IAuthorService extends IService<Author> {

    Author getAuthorByUserId(String userId);

    Author getAuthorById(String authorId);

    List<Author> getAuthorsByPenName(String penName);

    Boolean registerAuthor(AuthorRequest authorRequest);

    Boolean updatePenNameById(String authorId, String penName);

    Boolean updateStatusById(String authorId, int status);

    Boolean removeAuthor(String authorId);
}

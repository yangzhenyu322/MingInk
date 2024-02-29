package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.Book;
import com.mingink.article.api.domain.vo.BookRequest;
import com.mingink.article.mapper.BookMapper;
import com.mingink.article.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Book 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class BookService extends ServiceImpl<BookMapper, Book> implements IBookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> searchBooksByName(String name) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);

        return bookMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean insertBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setAuthorId(bookRequest.getAuthorId());
        book.setName(bookRequest.getName());
        book.setDescription(bookRequest.getDescription());
        book.setPicUrl(bookRequest.getPicUrl());
        book.setStatus(0);
        book.setVisitCount(0L);
        book.setCollectCount(0L);
        book.setWordCount(0);
        book.setCommentCount(0);
        book.setScore(0.0f);
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());

        return bookMapper.insert(book) > 0;
    }

    @Override
    public Boolean updateBookInfo(BookRequest bookRequest, Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        book.setAuthorId(bookRequest.getAuthorId());
        book.setName(bookRequest.getName());
        book.setDescription(bookRequest.getDescription());
        book.setPicUrl(bookRequest.getPicUrl());
        book.setUpdateTime(LocalDateTime.now());

        return bookMapper.updateById(book) > 0;
    }

    @Override
    public Boolean updateBookStatus(Long bookId, int status) {
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(status);

        return bookMapper.updateById(book) > 0;
    }

    @Override
    public Boolean updateBookVisitCount(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        book.setVisitCount(bookMapper.selectById(bookId).getVisitCount() + 1);

        return bookMapper.updateById(book) > 0;
    }
}
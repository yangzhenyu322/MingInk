package com.mingink.article.service.impl;

import com.mingink.article.domain.entity.Book;
import com.mingink.article.mapper.BookMapper;
import com.mingink.article.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Book 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class BookService extends ServiceImpl<BookMapper, Book> implements IBookService {

}

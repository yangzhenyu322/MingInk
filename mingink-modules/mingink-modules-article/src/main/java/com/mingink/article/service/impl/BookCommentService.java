package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.BookComment;
import com.mingink.article.mapper.BookCommentMapper;
import com.mingink.article.service.IBookCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Comment 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class BookCommentService extends ServiceImpl<BookCommentMapper, BookComment> implements IBookCommentService {

}

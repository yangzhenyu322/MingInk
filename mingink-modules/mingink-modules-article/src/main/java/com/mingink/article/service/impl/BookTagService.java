package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.BookTag;
import com.mingink.article.mapper.BookTagMapper;
import com.mingink.article.service.IBookTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * BookTag 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class BookTagService extends ServiceImpl<BookTagMapper, BookTag> implements IBookTagService {

}

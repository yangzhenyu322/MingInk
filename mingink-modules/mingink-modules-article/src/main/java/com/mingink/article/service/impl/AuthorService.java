package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.Author;
import com.mingink.article.mapper.AuthorMapper;
import com.mingink.article.service.IAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class AuthorService extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

}
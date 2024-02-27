package com.mingink.article.service.impl;

import com.mingink.article.domain.entity.Chapter;
import com.mingink.article.mapper.ChapterMapper;
import com.mingink.article.service.IChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Chapter 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {

}

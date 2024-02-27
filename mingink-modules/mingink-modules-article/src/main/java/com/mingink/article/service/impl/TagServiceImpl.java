package com.mingink.article.service.impl;

import com.mingink.article.domain.entity.Tag;
import com.mingink.article.mapper.TagMapper;
import com.mingink.article.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Tag 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}

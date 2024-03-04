package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.entity.Tag;

/**
 * Tag 服务类
 * @author ZenSheep
 * @since 2024-02-27
 */
public interface ITagService extends IService<Tag> {
    public String getTagNameById(Long tagId);

    Tag getTagById(Long tagId);
}

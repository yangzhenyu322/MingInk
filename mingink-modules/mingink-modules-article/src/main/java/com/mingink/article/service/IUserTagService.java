package com.mingink.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.article.api.domain.entity.UserTag;

import java.util.List;

/**
 * @author ZenSheep
 * @since 2024-02-29
 */
public interface IUserTagService extends IService<UserTag> {

    List<String> getTagKeysByUserId(String userId);

    Boolean removeUserTag(UserTag userTag);
}

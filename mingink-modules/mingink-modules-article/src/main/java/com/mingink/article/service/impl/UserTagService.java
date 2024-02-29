package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.UserTag;
import com.mingink.article.mapper.TagMapper;
import com.mingink.article.mapper.UserTagMapper;
import com.mingink.article.service.IUserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZenSheep
 * @since 2024-02-29
 */
@Service
public class UserTagService extends ServiceImpl<UserTagMapper, UserTag> implements IUserTagService {
    @Autowired
    private UserTagMapper userTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<String> getTagKeysByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        List<UserTag> userTags = userTagMapper.selectByMap(map);
        List<Long> tagIds = userTags.stream().map(userTag -> {
            return userTag.getTagId();
        }).collect(Collectors.toList());

        List<String> tagKeys = tagMapper.selectBatchIds(tagIds).stream()
                .map(tag -> {
                    return tag.getName();
                }).collect(Collectors.toList());

        return tagKeys;
    }

    @Override
    public Boolean removeUserTag(UserTag userTag) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userTag.getUserId());
        map.put("tag_id", userTag.getTagId());

        return userTagMapper.deleteByMap(map) > 0;
    }
}

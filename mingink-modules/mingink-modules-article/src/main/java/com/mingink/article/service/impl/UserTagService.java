package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.GorseUser;
import com.mingink.article.api.domain.entity.Tag;
import com.mingink.article.api.domain.entity.UserTag;
import com.mingink.article.mapper.UserTagMapper;
import com.mingink.article.service.IGorseService;
import com.mingink.article.service.ITagService;
import com.mingink.article.service.IUserTagService;
import com.mingink.article.utils.GorseUtils;
import io.seata.spring.annotation.GlobalTransactional;
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
    private ITagService tagService;

    @Autowired
    private IGorseService gorseService;

    @Override
    public List<Tag> getUserTagsById(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        List<Tag> tags = userTagMapper.selectByMap(map).stream().map(userTag -> {
            return tagService.getTagById(userTag.getTagId());
        }).collect(Collectors.toList());

        return tags;
    }

    @Override
    @GlobalTransactional
    public Boolean addNewUserTag(UserTag userTag) {
        Boolean isInsertUserTagSuccess = userTagMapper.insert(userTag) > 0;

        // 更新Gorse User
        GorseUser gorseUser = gorseService.getGorseUserById(userTag.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userTag.getUserId());
        List<String> tagNames = userTagMapper.selectByMap(map).stream().map(userTagItem -> {
            return tagService.getTagNameById(userTagItem.getTagId());
        }).collect(Collectors.toList());
        String tagNamesStr = GorseUtils.tagNamesToStr(tagNames);
        gorseUser.setLabels(tagNamesStr);
        gorseService.updateGorseUser(gorseUser);

        return isInsertUserTagSuccess;
    }

    @Override
    public Boolean removeUserTag(UserTag userTag) {
        Map<String, Object> deleteUserTagMap = new HashMap<>();
        deleteUserTagMap.put("user_id", userTag.getUserId());
        deleteUserTagMap.put("tag_id", userTag.getTagId());
        Boolean isRemoveUserTagSuccess = userTagMapper.deleteByMap(deleteUserTagMap) > 0;

        // 更新Gorse User
        GorseUser gorseUser = gorseService.getGorseUserById(userTag.getUserId());

        Map<String, Object> updateGorseUserMap = new HashMap<>();
        updateGorseUserMap.put("user_id", userTag.getUserId());
        List<String> tagNames = userTagMapper.selectByMap(updateGorseUserMap).stream().map(userTagItem -> {
            return tagService.getTagNameById(userTagItem.getTagId());
        }).collect(Collectors.toList());
        String tagNamesStr = GorseUtils.tagNamesToStr(tagNames);
        gorseUser.setLabels(tagNamesStr);
        gorseService.updateGorseUser(gorseUser);

        return isRemoveUserTagSuccess;
    }

    @Override
    @GlobalTransactional
    public Boolean removeUserTagByUserId(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return userTagMapper.delete(queryWrapper) > 0;
    }
}

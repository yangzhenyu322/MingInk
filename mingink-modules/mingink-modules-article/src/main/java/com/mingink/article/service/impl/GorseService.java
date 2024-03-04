package com.mingink.article.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mingink.article.api.domain.dto.GorseFeedbackRequest;
import com.mingink.article.api.domain.dto.GorseItemRequest;
import com.mingink.article.api.domain.dto.GorseUserRequest;
import com.mingink.article.api.domain.entity.GorseFeedback;
import com.mingink.article.api.domain.entity.GorseItem;
import com.mingink.article.api.domain.entity.GorseUser;
import com.mingink.article.domain.pojo.GorseClient;
import com.mingink.article.mapper.GorseFeedbackMapper;
import com.mingink.article.mapper.GorseItemsMapper;
import com.mingink.article.mapper.GorseUsersMapper;
import com.mingink.article.service.IGorseService;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZenSheep
 * @since 2024-02-29
 */
@Slf4j
@Service
public class GorseService implements IGorseService {
    @Autowired
    private GorseClient gorseClient;

    @Autowired
    private GorseFeedbackMapper gorseFeedbackMapper;

    @Autowired
    private GorseItemsMapper gorseItemsMapper;

    @Autowired
    private GorseUsersMapper gorseUsersMapper;

    @SneakyThrows
    @Override
    public List<JSONObject> getLatestRecommendBooks() {
        return gorseClient.getLatest();
    }

    @SneakyThrows
    @Override
    public List<JSONObject> getLatestRecommendBooksByCategory(String category) {
        return gorseClient.getLatest(category);
    }

    @SneakyThrows
    @Override
    public List<JSONObject> getPopularRecommendBooks() {
        return gorseClient.getPopular();
    }

    @SneakyThrows
    @Override
    public List<JSONObject> getPopularRecommendBooksByCategory(String category) {
        return gorseClient.getPopular(category);
    }

    @SneakyThrows
    @Override
    public List<String> getRecommendBooksByUserId(String userId) {
        return gorseClient.getRecommend(userId);
    }

    @SneakyThrows
    @Override
    public List<String> getRecommendBooksByUsersIdAndCategory(String userId, String category) {
        return gorseClient.getRecommend(userId, category);
    }

    @SneakyThrows
    @Override
    public List<JSONObject> getSimilarRecommendBookByBookId(String bookId) {
        return gorseClient.getSimilar(bookId);
    }

    @SneakyThrows
    @Override
    public List<JSONObject> getSimilarRecommendBookByBookIdAndCategory(String bookId, String category) {
        return gorseClient.getSimilar(bookId, category);
    }

    @Override
    @GlobalTransactional
    public boolean addNewGorseUser(GorseUserRequest gorseUserRequest) {
        GorseUser gorseUser = new GorseUser();
        gorseUser.setUserId(gorseUserRequest.getUserId());
        gorseUser.setLabels(gorseUserRequest.getLabels());
        gorseUser.setComment("");
        gorseUser.setSubscribe("[]");

        return gorseUsersMapper.insert(gorseUser) > 0;
    }

    @Override
    @GlobalTransactional
    public boolean removeGorseUser(String userId) {
        return gorseUsersMapper.deleteById(userId) > 0;
    }

    @Override
    @GlobalTransactional
    public boolean updateGorseUser(GorseUserRequest gorseUserRequest) {
        GorseUser gorseUser = new GorseUser();
        gorseUser.setUserId(gorseUserRequest.getUserId());
        gorseUser.setLabels(gorseUserRequest.getLabels());
        gorseUser.setComment("");
        gorseUser.setSubscribe("[]");

        return gorseUsersMapper.updateById(gorseUser) > 0;
    }

    @Override
    public GorseItem getGorseItemById(String itemId) {
        return gorseItemsMapper.selectById(itemId);
    }

    @Override
    @GlobalTransactional
    public boolean addNewItem(GorseItemRequest gorseItemRequest) {
        GorseItem gorseItem = new GorseItem();
        gorseItem.setItemId(gorseItemRequest.getItemId());
        gorseItem.setTimeStamp(LocalDateTime.now());
        gorseItem.setLabels(gorseItemRequest.getLabels());
        gorseItem.setComment(gorseItemRequest.getComment());
        gorseItem.setIsHidden(false);
        gorseItem.setCategories(gorseItemRequest.getCategories());

        return gorseItemsMapper.insert(gorseItem) > 0;
    }

    @Override
    public boolean removeItem(String bookId) {
        return gorseItemsMapper.deleteById(bookId) > 0;
    }

    @Override
    @GlobalTransactional
    public boolean updateGorseItem(GorseItem gorseItem) {
        return gorseItemsMapper.updateById(gorseItem) > 0;
    }

    @Override
    @GlobalTransactional
    public boolean updateGorseItemHidden(String itemId, boolean isHidden) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("item_id", itemId);
        updateWrapper.set("is_hidden", isHidden);

        return gorseItemsMapper.update(null, updateWrapper) > 0;
    }

    @Override
    @GlobalTransactional
    public boolean addNewFeedBack(GorseFeedbackRequest gorseFeedbackRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("feedback_type", gorseFeedbackRequest.getFeedbackType());
        map.put("user_id", gorseFeedbackRequest.getUserId());
        map.put("item_id", gorseFeedbackRequest.getItemId());
        if (gorseFeedbackMapper.selectByMap(map).size() > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该反馈已存在，不能重复添加");
        }

        GorseFeedback gorseFeedback = new GorseFeedback();
        gorseFeedback.setFeedbackType(gorseFeedbackRequest.getFeedbackType());
        gorseFeedback.setUserId(gorseFeedbackRequest.getUserId());
        gorseFeedback.setItemId(gorseFeedbackRequest.getItemId());
        gorseFeedback.setTimeStamp(LocalDateTime.now());
        gorseFeedback.setComment("");

        return gorseFeedbackMapper.insert(gorseFeedback) > 0;
    }

    @Override
    @GlobalTransactional
    public boolean removeFeedBack(GorseFeedbackRequest gorseFeedbackRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("feedback_type", gorseFeedbackRequest.getFeedbackType());
        map.put("user_id", gorseFeedbackRequest.getUserId());
        map.put("item_id", gorseFeedbackRequest.getItemId());

        return gorseFeedbackMapper.deleteByMap(map) > 0;
    }
}
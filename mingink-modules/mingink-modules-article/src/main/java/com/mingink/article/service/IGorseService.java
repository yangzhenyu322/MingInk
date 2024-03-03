package com.mingink.article.service;

import com.alibaba.fastjson2.JSONObject;
import com.mingink.article.api.domain.dto.GorseFeedbackRequest;
import com.mingink.article.api.domain.dto.GorseItemRequest;
import com.mingink.article.api.domain.dto.GorseUserRequest;
import com.mingink.article.api.domain.entity.GorseItem;

import java.util.List;

/**
 * @author ZenSheep
 * @since 2024-02-29
 */
public interface IGorseService {
    List<JSONObject> getLatestRecommendBooks();

    List<JSONObject> getLatestRecommendBooksByCategory(String category);

    List<JSONObject> getPopularRecommendBooks();

    List<JSONObject> getPopularRecommendBooksByCategory(String category);

    List<String> getRecommendBooksByUserId(String userId);

    List<String> getRecommendBooksByUsersIdAndCategory(String userId, String category);

    List<JSONObject> getSimilarRecommendBookByBookId(String bookId);

    List<JSONObject> getSimilarRecommendBookByBookIdAndCategory(String bookId, String category);

    boolean addNewGorseUser(GorseUserRequest gorseUserRequest);

    boolean removeGorseUser(String userId);

    boolean updateGorseUser(GorseUserRequest gorseUserRequest);

    boolean addNewItem(GorseItemRequest gorseItemRequest);

    boolean removeItem(String bookId);

    boolean updateGorseItem(GorseItem gorseItem);

    boolean updateGorseItemHidden(String itemId, boolean isHidden);

    boolean addNewFeedBack(GorseFeedbackRequest gorseFeedbackRequest);

    boolean removeFeedBack(GorseFeedbackRequest gorseFeedbackRequest);
}

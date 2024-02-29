package com.mingink.article.service;

import com.alibaba.fastjson2.JSONObject;

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
}

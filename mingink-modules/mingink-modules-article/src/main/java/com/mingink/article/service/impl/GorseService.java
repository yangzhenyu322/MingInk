package com.mingink.article.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.mingink.article.domain.pojo.GorseClient;
import com.mingink.article.service.IGorseService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZenSheep
 * @since 2024-02-29
 */
@Slf4j
@Service
public class GorseService implements IGorseService {
    @Autowired
    private GorseClient gorseClient;

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
}
package com.mingink.article.controller;


import com.alibaba.fastjson2.JSONObject;
import com.mingink.article.api.domain.dto.GorseFeedbackRequest;
import com.mingink.article.api.domain.dto.GorseItemRequest;
import com.mingink.article.api.domain.dto.GorseUserRequest;
import com.mingink.article.api.domain.entity.GorseItem;
import com.mingink.article.api.domain.entity.GorseUser;
import com.mingink.article.service.IGorseService;
import com.mingink.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Gorse 前端控制器
 * @author ZenSheep
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/gorse")
@Tag(name = "Gorse 推荐接口")
public class GorseController {
    @Autowired
    private IGorseService gorseService;

    /**
     * 获取全站最新的小说推荐
     * @return
     */
    @GetMapping("/recommend/latest")
    @Operation(summary = "获取全站最新的小说推荐")
    public R<List<JSONObject>> getLatestRecommendBooks() {
        return R.ok(gorseService.getLatestRecommendBooks());
    }

    /**
     * 获取指定品类(Tag)下的最新小说推荐
     * @param category
     * @return
     */
    @GetMapping("/recommend/latest/category/{category}")
    @Operation(summary = "获取指定品类(Tag)下的最新小说推荐")
    public R<List<JSONObject>> getLatestRecommendBooksByCategory(@PathVariable("category") String category) {
        return R.ok(gorseService.getLatestRecommendBooksByCategory(category));
    }

    /**
     * 获取全站热门推荐
     * @return
     */
    @GetMapping("/recommend/popular")
    @Operation(summary = "获取全站热门推荐")
    public R<List<JSONObject>> getPopularRecommendBooks() {
        return R.ok(gorseService.getPopularRecommendBooks());
    }

    /**
     * 获取全站指定品类(Tag)的热门推荐
     * @return
     */
    @GetMapping("/recommend/popular/category/{category}")
    @Operation(summary = "获取全站指定品类(Tag)的热门推荐")
    public R<List<JSONObject>> getPopularRecommendBooksByCategory(@PathVariable("category") String category) {
        return R.ok(gorseService.getPopularRecommendBooksByCategory(category));
    }

    /**
     * 通过用户ID获取个性化推荐小说
     * @param userId
     * @return
     */
    @GetMapping("/recommend/userId/{userId}")
    @Operation(summary = "通过用户ID获取个性化推荐小说")
    public R<List<String>> getRecommendBooksByUserId(@PathVariable("userId") String userId) {
        return R.ok(gorseService.getRecommendBooksByUserId(userId));
    }

    /**
     * 通过用户ID和种类(Tag)名获取指定品类(Tag)下的个性化推荐
     * @param userId
     * @param category
     * @return
     */
    @GetMapping("/recommend/userId/{userId}/category/{category}")
    @Operation(summary = "通过用户ID和种类(Tag)名获取指定品类(Tag)下的个性化推荐")
    public R<List<String>> getRecommendBooksByUsersIdAndCategory(@PathVariable("userId") String userId,
                                                                 @PathVariable("category") String category) {
        return R.ok(gorseService.getRecommendBooksByUsersIdAndCategory(userId, category));
    }

    /**
     * 通过BookId获取与该小说相似的推荐
     * @param bookId
     * @return
     */
    @GetMapping("/recommend/similar/bookId/{bookId}")
    @Operation(summary = "通过BookId获取与该小说相似的推荐")
    public R<List<JSONObject>> getSimilarRecommendBookByBookId(@PathVariable("bookId") String bookId) {
        return R.ok(gorseService.getSimilarRecommendBookByBookId(bookId));
    }

    /**
     * 通过Book Id获取指定品类(Tag)下与该小说相似的推荐
     * @param bookId
     * @return
     */
    @GetMapping("/recommend/similar/bookId/{bookId}/category/{category}")
    @Operation(summary = "通过Book Id获取指定品类(Tag)下与该小说相似的推荐")
    public R<List<JSONObject>> getSimilarRecommendBookByBookIdAndCategory(@PathVariable("bookId") String bookId,
                                                                          @PathVariable("category") String category) {
        return R.ok(gorseService.getSimilarRecommendBookByBookIdAndCategory(bookId, category));
    }

    /**
     * 新增GorseUser
     * @param gorseUserRequest
     * @return
     */
    @PostMapping("/user")
    @Operation(hidden = true)
    public Boolean addNewGorseUser(@RequestBody GorseUserRequest gorseUserRequest) {
        return gorseService.addNewGorseUser(gorseUserRequest);
    }

    /**
     * 删除GorseUser
     * @param userId
     * @return
     */
    @DeleteMapping("/user/userId/{userId}")
    @Operation(hidden = true)
    public Boolean removeGorseUser(@PathVariable("userId") String userId) {
        return gorseService.removeGorseUser(userId);
    }

    /**
     * 更新Gorse User 信息
     * @param gorseUser
     * @return
     */
    @PutMapping("/user")
    @Operation(hidden = true)
    public Boolean updateGorseUser(@RequestBody GorseUser gorseUser) {
        return gorseService.updateGorseUser(gorseUser);
    }

    /**
     * 新增Gorse Item
     * @param gorseItemRequest
     * @return
     */
    @PostMapping("/item")
    @Operation(hidden = true)
    public Boolean addNewItem(@RequestBody GorseItemRequest gorseItemRequest) {
        return gorseService.addNewItem(gorseItemRequest);
    }

    /**
     * 删除Gorse Item
     * @param bookId
     * @return
     */
    @DeleteMapping("/item/bookId/{bookId}")
    @Operation(hidden = true)
    public Boolean removeItem(@PathVariable("bookId") String bookId) {
        return gorseService.removeItem(bookId);
    }

    @PutMapping("/item")
    @Operation(hidden = true)
    public Boolean updateItem(@RequestBody GorseItem gorseItem) {
        return gorseService.updateGorseItem(gorseItem);
    }

    /**
     * 新增用户反馈
     * @param gorseFeedbackRequest
     * @return
     */
    @PostMapping("/feedback")
    @Operation(hidden = true)
    public R<String> addNewFeedBack(@RequestBody GorseFeedbackRequest gorseFeedbackRequest) {
        if (!gorseService.addNewFeedBack(gorseFeedbackRequest)) {
            return R.fail("新增反馈失败");
        }

        return R.ok("新增反馈成功");
    }

    /**
     * 删除用户反馈
     * @param gorseFeedbackRequest
     * @return
     */
    @DeleteMapping("/feedback")
    @Operation(hidden = true)
    public R<String> removeFeedBack(@RequestBody GorseFeedbackRequest gorseFeedbackRequest) {
        if (!gorseService.removeFeedBack(gorseFeedbackRequest)) {
            return R.fail("删除反馈失败");
        }

        return R.ok("删除反馈成功");
    }

    @DeleteMapping("/feedback/userId/{userId}")
    @Operation(hidden = true)
    public Boolean removeFeedBackByUserId(@PathVariable("userId") String userId) {
        return gorseService.removeFeedBackByUserId(userId);
    }
}
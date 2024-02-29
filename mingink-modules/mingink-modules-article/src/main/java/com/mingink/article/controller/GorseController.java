package com.mingink.article.controller;


import com.alibaba.fastjson2.JSONObject;
import com.mingink.article.service.IGorseService;
import com.mingink.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Gorse 前端控制器
 * @author ZenSheep
 * @since 2024-02-29
 */
@RestController
@RequestMapping("/gorse")
@Api(value = "Gorse接口功能", tags = "ChapterController", description = "Gorse接口相关介绍")
public class GorseController {
    @Autowired
    private IGorseService gorseService;

    /**
     * 获取全站最新的小说推荐
     * @return
     */
    @GetMapping("/recommend/latest")
    @ApiOperation("获取全站最新的小说推荐")
    public R<List<JSONObject>> getLatestRecommendBooks() {
        return R.ok(gorseService.getLatestRecommendBooks());
    }

    /**
     * 获取指定品类(Tag)下的最新小说推荐
     * @param category
     * @return
     */
    @GetMapping("/recommend/latest/category/{category}")
    @ApiOperation("获取指定品类(Tag)下的最新小说推荐")
    public R<List<JSONObject>> getLatestRecommendBooksByCategory(@PathVariable("category") String category) {
        return R.ok(gorseService.getLatestRecommendBooksByCategory(category));
    }

    /**
     * 获取全站热门推荐
     * @return
     */
    @GetMapping("/recommend/popular")
    @ApiOperation("获取全站热门推荐")
    public R<List<JSONObject>> getPopularRecommendBooks() {
        return R.ok(gorseService.getPopularRecommendBooks());
    }

    /**
     * 获取全站指定品类(Tag)的热门推荐
     * @return
     */
    @GetMapping("/recommend/popular/category/{category}")
    @ApiOperation("获取全站指定品类(Tag)的热门推荐")
    public R<List<JSONObject>> getPopularRecommendBooksByCategory(@PathVariable("category") String category) {
        return R.ok(gorseService.getPopularRecommendBooksByCategory(category));
    }

    /**
     * 通过用户ID获取个性化推荐小说
     * @param userId
     * @return
     */
    @GetMapping("/recommend/userId/{userId}")
    @ApiOperation("通过用户ID获取个性化推荐小说")
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
    @ApiOperation("通过用户ID和种类(Tag)名获取指定品类(Tag)下的个性化推荐")
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
    @ApiOperation("通过BookId获取与该小说相似的推荐")
    public R<List<JSONObject>> getSimilarRecommendBookByBookId(@PathVariable("bookId") String bookId) {
        return R.ok(gorseService.getSimilarRecommendBookByBookId(bookId));
    }

    /**
     * 通过Book Id获取指定品类(Tag)下与该小说相似的推荐
     * @param bookId
     * @return
     */
    @GetMapping("/recommend/similar/bookId/{bookId}/category/{category}")
    @ApiOperation("通过Book Id获取指定品类(Tag)下与该小说相似的推荐")
    public R<List<JSONObject>> getSimilarRecommendBookByBookIdAndCategory(@PathVariable("bookId") String bookId,
                                                                          @PathVariable("category") String category) {
        return R.ok(gorseService.getSimilarRecommendBookByBookIdAndCategory(bookId, category));
    }
}
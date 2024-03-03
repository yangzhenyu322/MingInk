package com.mingink.article.controller;


import com.mingink.article.api.domain.dto.BaseChapterRequest;
import com.mingink.article.api.domain.dto.AddChapterRequest;
import com.mingink.article.service.IChapterService;
import com.mingink.article.service.impl.ChapterService;
import com.mingink.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 小说章节 前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/chapter")
@Api(value = "小说章节接口功能", tags = "ChapterController", description = "小说章节接口相关介绍")
public class ChapterController {

    @Autowired
    private IChapterService iChapterService;

    /**
     * 章节发布或者存为草稿
     * @param addChapterRequest
     * @param request
     */
    @PostMapping("/add")
    @ApiOperation("章节存为草稿/发布 status: 0-草稿 1-发布")
    public R<?> addChapter(@RequestBody AddChapterRequest addChapterRequest, HttpServletRequest request) {
        return R.ok(iChapterService.addChapter(addChapterRequest));
    }

    /**
     * 修改小说章节状态
     * @param baseChapterRequest
     * @param request
     */
    @PostMapping("/update/status")
    @ApiOperation("修改小说章节状态")
    public R<?> updateChapterStatus(@RequestBody BaseChapterRequest baseChapterRequest, HttpServletRequest request) {
        return R.ok(iChapterService.updateChapterStatus(baseChapterRequest));
    }
}

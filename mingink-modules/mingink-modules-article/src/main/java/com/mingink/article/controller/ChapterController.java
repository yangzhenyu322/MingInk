package com.mingink.article.controller;

import com.mingink.article.api.domain.dto.ChapterRequest;
import com.mingink.article.api.domain.dto.PageChapterCatalogRequest;
import com.mingink.article.api.domain.entity.Chapter;
import com.mingink.article.api.domain.vo.ChapterCatalog;
import com.mingink.article.api.domain.vo.PageChapterCatalog;
import com.mingink.article.service.IChapterService;
import com.mingink.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private IChapterService chapterService;

    @GetMapping("/catalogs/bookId/{bookId}/status/{status}")
    @ApiOperation("通过小说ID和状态码查询所有(status为0-草稿、1-已发布、2-下降)章节目录信息")
    public R<List<ChapterCatalog>> getChapterCatalogsByBookIdAndStatus(@PathVariable("bookId") Long bookId,
                                                                       @PathVariable("status") Integer status) {
        return R.ok(chapterService.getChapterCatalogsByBookIdAndStatus(bookId, status));
    }

    @GetMapping("/catalogs/page")
    @ApiOperation("通过小说ID分页查询章节(status为0-草稿、1-已发布、2-下降)目录信息(升序排序)")
    public R<PageChapterCatalog> getPageChapterCatalogsByBookId(@RequestBody PageChapterCatalogRequest pageChapterCatalogRequest) {
        return R.ok(chapterService.getPageChapterCatalogsByBookId(pageChapterCatalogRequest));
    }

    @GetMapping("/chapterId/{chapterId}")
    @ApiOperation("通过小说ID查询章节详细内容")
    public R<Chapter> getChapterById(@PathVariable("chapterId") Long chapterId) {
        return R.ok(chapterService.getChapterById(chapterId));
    }

    /**
     * 章节发布或者存为草稿
     * @param chapterRequest
     */
    @PostMapping("/new")
    @ApiOperation("章节存为草稿/发布 status: 0-草稿 1-发布")
    public R<String> addChapter(@RequestBody ChapterRequest chapterRequest) {
        if (!chapterService.addChapter(chapterRequest)) {
            return R.fail("创建新的章节失败");
        }
        return R.ok("创建新的章节成功");
    }

    @PutMapping("/chapterId/{chapterId}")
    @ApiOperation("通过小说Id修改小说内容")
    public R<String> updateChapter(@RequestBody ChapterRequest chapterRequest, @PathVariable("chapterId") Long chapterId) {
        if (!chapterService.updateChapter(chapterRequest, chapterId)) {
            return R.fail("修改小说内容失败");
        }
        return R.ok("修改小说内容成功");
    }

    /**
     * 修改小说章节状态
     */
    @PutMapping("/chapterId/{chapterId}/status/{status}")
    @ApiOperation("修改小说章节状态 status: 0-草稿 1-发布 2-下架")
    public R<String> updateChapterStatus(@PathVariable("chapterId") Long chapterId,
                                    @PathVariable("status") Integer status) {
        if (!chapterService.updateChapterStatus(chapterId, status)) {
            return R.fail("修改小说章节状态失败");
        }
        return R.ok("修改小说章节状态成功");
    }

    @DeleteMapping("/chapterId/{chapterId}")
    @ApiOperation("通过章节ID删除小说章节")
    public R<String> removeChapterById(@PathVariable("chapterId") Long chapterId) {
        if (!chapterService.removeChapterById(chapterId)) {
            return R.fail("删除小说章节失败");
        }
        return R.ok("删除小说章节成功");
    }
}
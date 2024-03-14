package com.mingink.article.controller;

import com.mingink.article.api.domain.entity.BookTag;
import com.mingink.article.api.domain.entity.Tag;
import com.mingink.article.service.IBookTagService;
import com.mingink.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小说标签 前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/book-tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "小说标签接口")
public class BookTagController {
    @Autowired
    private IBookTagService bookTagService;

    @GetMapping("/bookId/{bookId}")
    @Operation(summary = "通过小说ID查询标签信息")
    public R<List<Tag>> getBookTagsById(@PathVariable("bookId") Long bookId) {
        return R.ok(bookTagService.getBookTagsById(bookId));
    }

    @PostMapping("/new")
    @Operation(summary = "给小说添加新的单个标签")
    public R<String> addNewBookTag(@RequestBody BookTag bookTag) {
        if (!bookTagService.addNewBookTag(bookTag)) {
            return R.fail("添加新的小说标签失败");
        }

        return R.ok("添加新的小说标签成功");
    }

    @DeleteMapping("/remove")
    @Operation(summary = "移除小说的单个标签")
    public R<String> removeBookTag(@RequestBody BookTag bookTag) {
        if (!bookTagService.removeBookTag(bookTag)) {
            return R.fail("移除小说标签失败");
        }

        return R.ok("移除小说标签成功");
    }
}

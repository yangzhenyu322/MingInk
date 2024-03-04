package com.mingink.article.controller;

import com.mingink.article.api.domain.entity.Tag;
import com.mingink.article.service.ITagService;
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
 * 标签控制器
 * @Author: ZenSheep
 * @Date: 2024/3/4 14:44
 */
@RestController
@RequestMapping("/tag")
@Api(value = "标签接口功能", tags = "TagController", description = "标签接口相关介绍")
public class TagController {
    @Autowired
    private ITagService tagService;

    @GetMapping("/tagId/{tagId}")
    @ApiOperation("通过标签ID获取标签信息")
    public R<Tag> getTagNameById(@PathVariable("tagId") Long tagId) {
        return R.ok(tagService.getById(tagId));
    }

    @GetMapping("/list")
    @ApiOperation("获取所有的标签信息")
    public R<List<Tag>> getAllTagsInfo() {
        return R.ok(tagService.list());
    }
}
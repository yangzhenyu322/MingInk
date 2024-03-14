package com.mingink.article.controller;

import com.mingink.article.api.domain.entity.Tag;
import com.mingink.article.api.domain.entity.UserTag;
import com.mingink.article.service.IUserTagService;
import com.mingink.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserTag 控制器
 * @Author: ZenSheep
 * @Date: 2024/2/29 16:49
 */
@RestController
@RequestMapping("/user-tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "用户标签接口")
public class UserTagController {
    @Autowired
    private IUserTagService userTagService;

    /**
     * 通过用户ID查询Tags
     * @param userId
     * @return
     */
    @GetMapping("/userId/{userId}")
    @Operation(summary = "通过用户ID查询标签信息")
    public R<List<Tag>> getUserTagsById(@PathVariable("userId") String userId) {
        return R.ok(userTagService.getUserTagsById(userId));
    }

    /**
     * 通过用户ID和TagID为用户添加Tag
     * @return
     */
    @PostMapping("/new")
    @Operation(summary = "给用户添加新的单个标签")
    public R<String> addNewBookTag(@RequestBody UserTag userTag) {
        if (!userTagService.addNewUserTag(userTag)) {
            return R.fail("添加新的用户标签失败");
        }

        return R.ok("添加新的用户标签成功");
    }

    @DeleteMapping("/remove")
    @Operation(summary = "移除用户的单个标签")
    public R<String> removeUserTag(@RequestBody UserTag userTag) {
        if (!userTagService.removeUserTag(userTag)) {
            return R.fail("删除UserTag失败");
        }

        return R.ok("删除UserTag成功");
    }

    /**
     * 通过用户ID移除其所有Tag
     */
    @DeleteMapping("/userId/{userId}")
    @Operation(hidden = true)
    public Boolean removeUserTagByUserId(@PathVariable("userId") String userId) {
        return userTagService.removeUserTagByUserId(userId);
    }
}
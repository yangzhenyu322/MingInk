package com.mingink.article.controller;

import com.mingink.article.api.domain.entity.UserTag;
import com.mingink.article.service.IUserTagService;
import com.mingink.common.core.domain.R;
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
public class UserTagController {
    @Autowired
    private IUserTagService userTagService;

    /**
     * 通过用户ID查询Tag Keys
     * @param userId
     * @return
     */
    @GetMapping("/list/userId/{userId}")
    public R<List<String>> getUserTagList(@PathVariable("userId") String userId) {
        return R.ok(userTagService.getTagKeysByUserId(userId));
    }

    /**
     * 通过用户ID和TagID为用户添加Tag
     * @return
     */
    @PostMapping("/add")
    public R<String> addUserTag(@RequestBody UserTag userTag) {
        Boolean isSuccess = userTagService.save(userTag);

        if (isSuccess) {
            return R.fail("添加UserTag失败");
        }

        return R.ok("添加UserTag成功");
    }

    @DeleteMapping("/remove")
    public R<String> removeUserTag(@RequestBody UserTag userTag) {
        Boolean isSuccess = userTagService.removeUserTag(userTag);

        if (isSuccess) {
            return R.fail("删除UserTag失败");
        }

        return R.ok("删除UserTag成功");
    }
}
package com.mingink.article.controller;


import com.mingink.article.api.domain.dto.AuthorRequest;
import com.mingink.article.api.domain.entity.Author;
import com.mingink.article.service.IAuthorService;
import com.mingink.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作家前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/author")
@Api(value = "作家接口功能", tags = "AuthorController", description = "作家接口相关介绍")
public class AuthorController {
    @Autowired
    private IAuthorService authorService;

    @GetMapping("/userId/{userId}")
    @ApiOperation("通过用户ID获取作者信息")
    public R<Author> getAuthorByUserId(@PathVariable("userId") String userId) {
        return R.ok(authorService.getAuthorByUserId(userId));
    }

    @GetMapping("/penName/{penName}")
    @ApiOperation("通过作者笔名模糊查询作者信息")
    public R<List<Author>> getAuthorsByPenName(@PathVariable("penName") String penName) {
        return R.ok(authorService.getAuthorsByPenName(penName));
    }

    @PostMapping("/register")
    @ApiOperation("注册成为作者")
    public R<String> registerAuthor(@RequestBody AuthorRequest authorRequest) {
        if (!authorService.registerAuthor(authorRequest)) {
            return R.fail("注册作者失败");
        }

        return R.ok("注册作者成功");
    }

    @PutMapping("/authorId/{authorId}/penName/{penName}")
    @ApiOperation("修改作者笔名")
    public R<String> updatePenNameById(@PathVariable("authorId") String authorId,
                                       @PathVariable("penName") String penName) {
        if (!authorService.updatePenNameById(authorId, penName)) {
            return R.fail("修改笔名失败");
        }

        return R.ok("修改笔名成功");
    }

    @PutMapping("/authorId/{authorId}/status/{status}")
    @ApiOperation("修改作者状态：0-正常,1-禁用")
    public R<String> updateStatusById(@PathVariable("authorId") String authorId,
                                      @PathVariable("status") int status) {
        if (!authorService.updateStatusById(authorId, status)) {
            return R.fail("修改作者状态失败");
        }

        return R.ok("修改作者状态成功");
    }

    @DeleteMapping("/authorId/{authorId}")
//    @ApiOperation("注销作者账户")
    // TODO
    public R<String> removeAuthor(@PathVariable("authorId") String authorId) {
        if (!authorService.removeAuthor(authorId)) {
            return R.fail("注销作者账户失败");
        }

        return R.ok("注销作者账户成功");
    }
}
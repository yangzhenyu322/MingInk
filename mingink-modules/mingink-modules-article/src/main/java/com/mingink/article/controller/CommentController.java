package com.mingink.article.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 小说评论 前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/comment")
@Api(value = "小说评论接口功能", tags = "CommentController", description = "小说评论接口相关介绍")
public class CommentController {

}

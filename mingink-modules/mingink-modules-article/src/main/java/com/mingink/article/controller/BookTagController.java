package com.mingink.article.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 小说标签 前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/book-tag")
@Api(value = "小说标签接口功能", tags = "BookTagController", description = "小说标签接口相关介绍")
public class BookTagController {

}

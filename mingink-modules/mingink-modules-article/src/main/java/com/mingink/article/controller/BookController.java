package com.mingink.article.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小说控制类
 * @Author: ZenSheep
 * @Date: 2024/2/26 21:57
 */
@RestController
@RequestMapping("/book")
@Api(value = "小说接口功能", tags = "BookController", description = "小说接口相关介绍")
public class BookController {

}
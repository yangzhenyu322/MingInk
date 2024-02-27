package com.mingink.article.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 小说前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/book")
@Api(value = "小说接口功能", tags = "BookController", description = "小说接口相关介绍")
public class BookController {

}
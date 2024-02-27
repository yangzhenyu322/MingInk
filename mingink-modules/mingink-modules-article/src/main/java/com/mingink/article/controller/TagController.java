package com.mingink.article.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 标签 前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/tag")
@Api(value = "标签接口功能", tags = "TagController", description = "标签接口相关介绍")
public class TagController {

}

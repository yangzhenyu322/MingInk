package com.mingink.article.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 小说章节 前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/chapter")
@Api(value = "小说章节接口功能", tags = "ChapterController", description = "小说章节接口相关介绍")
public class ChapterController {

}

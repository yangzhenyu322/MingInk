package com.mingink.article.controller;


import com.mingink.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 作家前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/author")
@Api(value = "作家接口功能", tags = "AuthorController", description = "作家接口相关介绍")
public class AuthorController {
    @GetMapping("/vip")
    @ApiOperation("VIP作家专用接口")
    public R<String> getVIP() {
        return R.ok("vip");
    }
}
package com.mingink.system.controller;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.MentalTestInsReq;
import com.mingink.system.api.domain.entity.MentalTest;
import com.mingink.system.service.IMentalTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/7/7 13:01
 * @description 心理测评相关接口
 */

@RestController
@RequestMapping("/mentalTest")
@Tag(name = "心理测评接口")
public class MentalTestController {

    @Autowired
    private IMentalTestService mentalTestService;

    /**
     * 保存测评结果
     */
    @PostMapping("/save")
    @Operation(summary = "保存心理测评结果信息")
    public R<Boolean> saveMentalTest(@RequestBody MentalTestInsReq mentalTestInsReq, HttpServletRequest request) {
        return mentalTestService.insertMentalTest(mentalTestInsReq, request);
    }

    /**
     * 用户测评结果查询
     */
    @GetMapping("/history")
    @Operation(summary = "查询心理测评结果信息")
    public R<List<MentalTest>> getMentalTestsByUser(HttpServletRequest request) {
        return mentalTestService.getMentalTestsByUser(request);
    }
}

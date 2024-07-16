package com.mingink.system.controller;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.DeleteReq;
import com.mingink.system.api.domain.dto.SignMessageAddReq;
import com.mingink.system.api.domain.entity.SignMessage;
import com.mingink.system.api.domain.vo.SignRecordVO;
import com.mingink.system.service.ISignMessageService;
import com.mingink.system.service.ISignRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/7/15 14:42
 * @description 用户打卡签文接口
 */
@RestController
@RequestMapping("/sign")
@Tag(name = "用户签文相关接口")
public class SignController {
    @Autowired
    private ISignMessageService signMessageService;

    @Autowired
    private ISignRecordService signRecordService;

    @PostMapping("/add")
    @Operation(summary = "新增签文")
    public R<Boolean> addSign(@RequestBody SignMessageAddReq signMessageAddReq, HttpServletRequest request) {
        return signMessageService.insert(signMessageAddReq, request);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除签文")
    public R<Boolean> deleteSignById(@RequestBody DeleteReq deleteReq, HttpServletRequest request) {
        return signMessageService.delete(deleteReq.getId(), request);
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取所有签文")
    public R<List<SignMessage>> getAll(HttpServletRequest request) {
        return signMessageService.getAll(request);
    }

    @PostMapping("/update")
    @Operation(summary = "更新签文")
    public R<Boolean> update(@RequestBody SignMessage signMessage, HttpServletRequest request) {
        return signMessageService.update(signMessage, request);
    }

    @GetMapping("/checkin")
    @Operation(summary = "获取今日签文")
    public R<SignRecordVO> checkin(HttpServletRequest request) {
        return signRecordService.getDailySignature(request);
    }

    @GetMapping("/checkinHistory")
    @Operation(summary = "获取历史求签记录")
    public R<List<SignRecordVO>> checkinHistory(HttpServletRequest request) {
        return signRecordService.getAll(request);
    }

}

package com.mingink.system.controller;

import com.alipay.api.AlipayApiException;
import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.AliPay;
import com.mingink.system.api.domain.dto.AliRefund;
import com.mingink.system.service.IPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 支付控制器
 * @Author: ZenSheep
 * @Date: 2024/3/17 20:53
 */
@Slf4j
@RestController
@RequestMapping("/pay")
@Tag(name = "支付接口")
public class PayController {
    @Autowired
    private IPayService payService;

    @PostMapping("/page/payType/{payType}")
    @Operation(summary = "前往支付页面, payType可选值: aliPayStrategy-支付宝支付")
    // TODO aliPay的alipayTraceNo未被使用、加入订单模块
    public R<String> pay(@RequestBody AliPay aliPay, @PathVariable("payType") String payType, HttpServletResponse response) throws IOException {
        return R.ok(payService.getPayPage(aliPay, payType));
    }

    @PostMapping("/notify/payType/{payType}")
    @Operation(summary = "回调接口, 用户支付成功后支付宝官方会调用的接口来改变订单状态，无需前端调用")
    public String payNotify(@PathVariable("payType") String payType, HttpServletRequest request) throws AlipayApiException {
        return payService.payNotify(request, payType);
    }

    @PutMapping("/refund/payType/{payType}")
    @Operation(summary = "退款接口")
    public R<Boolean> refundPay(@RequestBody AliRefund aliRefund, @PathVariable("payType") String payType) throws AlipayApiException {
        return R.ok(payService.refundPay(aliRefund, payType));
    }
}
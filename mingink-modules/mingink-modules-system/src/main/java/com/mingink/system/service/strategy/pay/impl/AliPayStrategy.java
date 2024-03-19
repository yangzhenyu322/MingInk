package com.mingink.system.service.strategy.pay.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.mingink.system.api.domain.dto.AliPay;
import com.mingink.system.api.domain.dto.AliRefund;
import com.mingink.system.config.AliPayConfig;
import com.mingink.system.service.strategy.pay.IPayStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:03
 */
@Slf4j
@Service
public class AliPayStrategy implements IPayStrategy {
    @Resource
    private AliPayConfig aliPayConfig;

    @Override
    public String getPayPage(AliPay aliPay) {
        log.info("payInfo: {}", aliPay.toString());

        // 1. 创建Client， 通过SDK提供的Client调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), AliPayConfig.FORMAT, AliPayConfig.CHARSET, aliPayConfig.getAlipayPublicKey(), AliPayConfig.SIGN_TYPE);

        // 2. 创建 Request 并设置参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", aliPay.getTraceNo()); // 自己生成的订单号
        bizContent.put("total_amount", aliPay.getTotalAmount()); // 订单的总金额
        bizContent.put("subject", aliPay.getSubject()); // 支付的名称
        bizContent.put("product_code", aliPay.getProductCode()); // 固定配置
        bizContent.put("qr_pay_mode", aliPay.getArPayMode()); // PC扫码的支付方式
        request.setBizContent(bizContent.toString());

        // 3. 执行请求
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return form;
    }

    @Override
    public String payNotify(HttpServletRequest request) throws AlipayApiException {
        log.info("收到回调请求");
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) { // 交易成功
            log.info("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no"); // TODO 存储到数据库中

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), AliPayConfig.CHARSET); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                log.info("交易名称: {}", params.get("subject"));
                log.info("交易状态: {}", params.get("trade_status"));
                log.info("支付宝交易凭证号: {}", params.get("trade_no"));
                log.info("商户订单号: {}", params.get("out_trade_no"));
                log.info("交易金额: {}", params.get("total_amount"));
                log.info("买家在支付宝唯一id: {}", params.get("buyer_id"));
                log.info("买家付款时间: {}", params.get("gmt_payment"));
                log.info("买家付款金额: {}", params.get("buyer_pay_amount"));

                // 更新订单支付状态(未支付/已支付)
                log.info("订单支付成功");
//                ordersMapper.updateState(tradeNo, "已支付", gmtPayment, alipayTradeNo);
                return "success";
            }
        }

        return "fail";
    }

    @Override
    public Boolean refundPay(AliRefund aliRefund) throws AlipayApiException {
        // 1. 创建client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,
                aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), AliPayConfig.FORMAT, AliPayConfig.CHARSET,
                aliPayConfig.getAlipayPublicKey(), AliPayConfig.SIGN_TYPE);

        // 2. 创建Request并设置Reqeust参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", aliRefund.getAlipayTraceNo()); // 支付宝回调的订单流水号
        bizContent.put("refund_amount", aliRefund.getTotalAmount()); // 订单的总金额
        bizContent.put("out_request_no", aliRefund.getTraceNo()); // 我的订单号

        // 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());

        // 3. 执行请求
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) { // 退款成功
            log.info("订单[{}]退款成功--{}", aliRefund.getTraceNo(), aliRefund);

            // 4. 更新数据库状态
//            orderMapper.updatePayState(aliRefund.getTraceNo(), "已退款", now);
            return true;
        }

        log.info("订单[{}]退款失败--{}", aliRefund.getTraceNo(), response.getBody());
        return false;
    }
}
package com.mingink.order.service.strategy.pay.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.mingink.order.config.AliPayConfig;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import com.mingink.order.service.strategy.pay.IPayStrategy;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝相关参数配置：https://opendocs.alipay.com/open/59da99d0_alipay.trade.page.pay?pathHash=8e24911d&ref=api&scene=22
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:03
 */
@Slf4j
@Service
public class AliPayStrategy implements IPayStrategy {
    @Resource
    private AliPayConfig aliPayConfig;

    @Override
    @GlobalTransactional
    public String getPayPage(PayParams payParams) throws AlipayApiException {
        log.info("payInfo: {}", payParams.toString());

        // 1. 创建Client， 通过SDK提供的Client调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), AliPayConfig.FORMAT, AliPayConfig.CHARSET, aliPayConfig.getAlipayPublicKey(), AliPayConfig.SIGN_TYPE);

        // 2. 创建 Request 并设置参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl()); // 支付后后端回调 url 接口
        request.setReturnUrl(payParams.getReturnUrl()); // 支付后前端跳转 url 链接
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", payParams.getTradeNo()); // 自己生成的订单号
        bizContent.put("total_amount", payParams.getTotalAmount()); // 订单的总金额
        bizContent.put("subject", payParams.getSubject()); // 支付的名称
        // 销售产品码（必填），商家和支付宝签约的产品码。注：目前电脑支付场景下仅支持FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.put("qr_pay_mode", 2); // PC扫码的支付方式, 2-跳转模式，0、1、3、4-前置模式
        // 订单绝对超时时间, 格式为yyyy-MM-dd HH:mm:ss。超时时间范围：1m~15d
        bizContent.put("time_expire", payParams.getExpireTime());
        request.setBizContent(bizContent.toString());

        // 3. 执行请求,调用SDK生成支付表单
        return alipayClient.pageExecute(request).getBody();
    }

    /**
     *
     * @param request
     * @return 消息报文，消息服务会根据响应报文判断商户系统是否已经成功处理消息。
     * 如果HTTP同步响应报文返回 success 字符串，消息服务则认为消息已经处理成功，停止投递
     * 如果返回 failure ，表示消息获取失败，支付宝会根据投递重试策略重新发送消息到应用网关地址
     * @throws AlipayApiException
     */
    @Override
    @GlobalTransactional
    public PayNotifyResult orderPayNotify(HttpServletRequest request) throws AlipayApiException {
        log.info("开始处理支付宝回调请求");

        PayNotifyResult payNotifyResult = new PayNotifyResult();
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) { // 交易成功
            log.info("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            // 订单变更的信息
            payNotifyResult.setTradeNo(params.get("out_trade_no"));
            payNotifyResult.setTradeStatus(1);
            payNotifyResult.setOutTradeNo(params.get("trade_no"));
            payNotifyResult.setBuyerId(params.get("buyer_id"));
            payNotifyResult.setBuyerPayAmount(new BigDecimal(params.get("total_amount")));
            payNotifyResult.setReceiptAmount(new BigDecimal(params.get("receipt_amount")));

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), AliPayConfig.CHARSET); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                log.info("商户订单号: {}", params.get("out_trade_no"));
                log.info("交易名称: {}", params.get("subject"));
                log.info("支付宝交易凭证号: {}", params.get("trade_no"));
                log.info("交易金额: {}", params.get("total_amount"));
                log.info("实收金额: {}", params.get("receipt_amount"));
                log.info("交易状态: {}", params.get("trade_status"));
                log.info("买家在支付宝唯一id: {}", params.get("buyer_id"));
                log.info("买家付款时间: {}", params.get("gmt_payment"));
                log.info("买家付款金额: {}", params.get("buyer_pay_amount"));

                log.info("支付成功");
                payNotifyResult.setResult("success");
            } else {
                log.info("支付失败");
                payNotifyResult.setResult("failure");
            }
        }

        return payNotifyResult;
    }

    @Override
    @GlobalTransactional
    public Boolean orderRefundPay(PayRefundParams payRefundParams) throws AlipayApiException {
        // 1. 创建client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,
                aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), AliPayConfig.FORMAT, AliPayConfig.CHARSET,
                aliPayConfig.getAlipayPublicKey(), AliPayConfig.SIGN_TYPE);

        // 2. 创建Request并设置 Reqeust 参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", payRefundParams.getOutTradeNo()); // 支付宝回调的订单流水号
        bizContent.put("refund_amount", payRefundParams.getTotalAmount()); // 订单的总金额
        bizContent.put("out_request_no", payRefundParams.getTradeNo()); // 我的订单号

        // 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());

        // 3. 执行请求
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) { // 退款成功
            log.info("订单[{}]退款成功--{}", payRefundParams.getTradeNo(), payRefundParams);

            return true;
        }

        log.info("订单[{}]退款失败--{}", payRefundParams.getTradeNo(), payRefundParams);
        return false;
    }
}
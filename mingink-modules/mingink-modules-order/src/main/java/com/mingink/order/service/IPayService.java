package com.mingink.order.service;

import com.alipay.api.AlipayApiException;
import com.mingink.order.domain.dto.AliPay;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import com.mingink.system.api.domain.dto.AliRefund;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:01
 */
public interface IPayService {
    String getPayPage(PayParams payParams) throws AlipayApiException;

    PayNotifyResult orderPayNotify(HttpServletRequest request, Integer payMode) throws AlipayApiException;

    Boolean orderRefundPay(PayRefundParams payRefundParams) throws AlipayApiException;

    String getPayPage(AliPay aliPay, String payType);

    String getPayFront(AliPay aliPay, String payType);

    String payNotify(HttpServletRequest request, String payType) throws AlipayApiException;

    Boolean refundPay(AliRefund aliRefund, String payType) throws AlipayApiException;
}

package com.mingink.system.service;

import com.alipay.api.AlipayApiException;
import com.mingink.system.api.domain.dto.AliPay;
import com.mingink.system.api.domain.dto.AliRefund;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:01
 */
public interface IPayService {
    String getPayPage(AliPay aliPay, String payType);

    String payNotify(HttpServletRequest request, String payType) throws AlipayApiException;

    Boolean refundPay(AliRefund aliRefund, String payType) throws AlipayApiException;
}

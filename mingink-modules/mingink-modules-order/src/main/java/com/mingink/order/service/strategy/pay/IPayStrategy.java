package com.mingink.order.service.strategy.pay;

import com.alipay.api.AlipayApiException;
import com.mingink.order.domain.dto.AliPay;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import com.mingink.system.api.domain.dto.AliRefund;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:02
 */
public interface IPayStrategy {
    String getPayPage(PayParams payParams) throws AlipayApiException;

    PayNotifyResult orderPayNotify(HttpServletRequest request) throws AlipayApiException;

    Boolean orderRefundPay(PayRefundParams payRefundParams) throws AlipayApiException;

    String getPayPage(AliPay aliPay);

    String payNotify(HttpServletRequest request) throws AlipayApiException;

    Boolean refundPay(AliRefund aliRefund) throws AlipayApiException;

    String getPayFront(AliPay aliPay);
}

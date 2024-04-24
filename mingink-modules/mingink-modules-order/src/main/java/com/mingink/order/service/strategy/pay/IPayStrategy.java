package com.mingink.order.service.strategy.pay;

import com.alipay.api.AlipayApiException;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:02
 */
public interface IPayStrategy {
    String getPayPage(PayParams payParams) throws AlipayApiException;

    PayNotifyResult orderPayNotify(HttpServletRequest request) throws AlipayApiException;

    Boolean orderRefundPay(PayRefundParams payRefundParams) throws AlipayApiException;

}

package com.mingink.system.service.strategy.pay;

import com.alipay.api.AlipayApiException;
import com.mingink.system.api.domain.dto.AliPay;
import com.mingink.system.api.domain.dto.AliRefund;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:02
 */
public interface IPayStrategy {
    String getPayPage(AliPay aliPay);

    String payNotify(HttpServletRequest request) throws AlipayApiException;

    Boolean refundPay(AliRefund aliRefund) throws AlipayApiException;
}

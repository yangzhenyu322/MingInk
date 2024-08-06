package com.mingink.order.service.strategy.pay.impl;

import com.alipay.api.AlipayApiException;
import com.mingink.order.config.WxPayConfig;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import com.mingink.order.service.strategy.pay.IPayStrategy;
import com.mingink.order.utils.WxPayV3Util;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/4/13 13:38
 * @description
 */
@Slf4j
public class WxPayStrategy implements IPayStrategy {

    @Resource
    private WxPayConfig wxPayConfig;



    @Override
    public String getPayPage(PayParams payParams) throws AlipayApiException {
        return null;
    }

    @Override
    @GlobalTransactional
    public PayNotifyResult orderPayNotify(HttpServletRequest request) throws AlipayApiException {
        return null;
    }

    @Override
    public Boolean orderRefundPay(PayRefundParams payRefundParams) throws AlipayApiException {
        WxPayV3Util wxPayV3Util = new WxPayV3Util(payRefundParams);
        return wxPayV3Util.refund();
    }


}

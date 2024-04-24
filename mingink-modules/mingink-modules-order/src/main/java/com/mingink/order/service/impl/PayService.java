package com.mingink.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.mingink.order.domain.dto.PayNotifyResult;
import com.mingink.order.domain.dto.PayParams;
import com.mingink.order.domain.dto.PayRefundParams;
import com.mingink.order.service.IPayService;
import com.mingink.order.service.strategy.pay.IPayStrategy;
import com.mingink.order.domain.dto.AliPay;
import com.mingink.system.api.domain.dto.AliRefund;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/17 21:01
 */
@Slf4j
@Service
public class PayService implements IPayService {
    // Spring 会自动将Strategy接口的实现类注入到这个Map中，Key为Bean Id，value为对应的策略实现类
    @Autowired
    private Map<String, IPayStrategy> payStrategyMap;

    @Override
    @GlobalTransactional
    public String getPayPage(PayParams payParams) throws AlipayApiException {
        String payType = getPayTypeByMode(payParams.getPayMode());
        IPayStrategy targetPayStrategy = Optional.ofNullable(payStrategyMap.get(payType))
                .orElseThrow(() -> new IllegalArgumentException("非法支付类型：" + payType));
        return targetPayStrategy.getPayPage(payParams);
    }

    @Override
    public PayNotifyResult orderPayNotify(HttpServletRequest request, Integer payMode) throws AlipayApiException {
        String payType = getPayTypeByMode(payMode);
        IPayStrategy targetPayStrategy = Optional.ofNullable(payStrategyMap.get(payType))
                .orElseThrow(() -> new IllegalArgumentException("非法支付类型：" + payType));
        return targetPayStrategy.orderPayNotify(request);
    }

    @Override
    public Boolean orderRefundPay(PayRefundParams payRefundParams) throws AlipayApiException {
        String payType = getPayTypeByMode(payRefundParams.getPayMode());
        IPayStrategy targetPayStrategy = Optional.ofNullable(payStrategyMap.get(payType))
                .orElseThrow(() -> new IllegalArgumentException("非法支付类型：" + payType));

        return targetPayStrategy.orderRefundPay(payRefundParams);
    }


    public static String getPayTypeByMode(Integer payMode) {
        if (payMode == 0) {
            return "wxPayStrategy";
        }
        return "aliPayStrategy";
    }
}

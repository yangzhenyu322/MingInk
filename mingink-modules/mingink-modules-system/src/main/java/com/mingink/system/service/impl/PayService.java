package com.mingink.system.service.impl;

import com.alipay.api.AlipayApiException;
import com.mingink.system.api.domain.dto.AliPay;
import com.mingink.system.api.domain.dto.AliRefund;
import com.mingink.system.service.IPayService;
import com.mingink.system.service.strategy.pay.IPayStrategy;
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
    public String getPayPage(AliPay aliPay, String payType) {
        IPayStrategy targetPayStrategy = Optional.ofNullable(payStrategyMap.get(payType))
                .orElseThrow(() -> new IllegalArgumentException("非法支付类型：" + payType));
        return targetPayStrategy.getPayPage(aliPay);
    }

    @Override
    public String payNotify(HttpServletRequest request, String payType) throws AlipayApiException {
        IPayStrategy targetPayStrategy = Optional.ofNullable(payStrategyMap.get(payType))
                .orElseThrow(() -> new IllegalArgumentException("非法支付类型：" + payType));
        return targetPayStrategy.payNotify(request);
    }

    @Override
    public Boolean refundPay(AliRefund aliRefund, String payType) throws AlipayApiException {
        IPayStrategy targetPayStrategy = Optional.ofNullable(payStrategyMap.get(payType))
                .orElseThrow(() -> new IllegalArgumentException("非法支付类型：" + payType));
        return targetPayStrategy.refundPay(aliRefund);
    }
}

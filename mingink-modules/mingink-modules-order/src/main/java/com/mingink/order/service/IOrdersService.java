package com.mingink.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.order.domain.dto.OrderRequest;
import com.mingink.order.domain.entity.Orders;
import com.mingink.order.domain.vo.OrderVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
public interface IOrdersService extends IService<Orders> {

    OrderVO createOrder(OrderRequest orderRequest);

    String payNotify(HttpServletRequest request, Integer payMode);

    Boolean refundPay(String orderId);
}

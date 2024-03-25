package com.mingink.order.controller;

import com.mingink.common.core.domain.R;
import com.mingink.order.domain.dto.OrderRequest;
import com.mingink.order.domain.entity.Orders;
import com.mingink.order.domain.vo.OrderVO;
import com.mingink.order.service.IOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/21 19:45
 */
@RestController
@RequestMapping("/order")
@Tag(name = "订单接口")
public class OrderController {
    @Autowired
    private IOrdersService ordersService;

    @GetMapping("/userId/{userId}")
    @Operation(hidden = true)
//    @Operation(summary = "查询某用户的所有订单信息")
    public R<List<Orders>> getOrdersByUserId(@PathVariable("userId") String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        return R.ok(ordersService.listByMap(map));
    }

    @PostMapping("/new")
    @Operation(summary = "创建订单, 返回订单相关信息和支付页面，前端如何处理支付页面参考博客的第二种方法：https://blog.csdn.net/weixin_47284756/article/details/122602293#:~:text=1.%E7%94%9F%E6%88%90%E4%BA%8C%E7%BB%B4%E7%A0%81%E5%88%B0%E9%A1%B5%E9%9D%A2%E9%87%8C%2C%E7%94%A8%E6%88%B7%E8%BF%9B%E8%A1%8C%E6%89%AB%E7%A0%81%E6%94%AF%E4%BB%98%20-----%E5%89%8D%E7%BD%AE%E6%A8%A1%E5%BC%8F%202.%E8%B7%B3%E8%BD%AC%E5%88%B0%E6%94%AF%E4%BB%98%E5%AE%9D%E7%9A%84%E9%A1%B5%E9%9D%A2%E9%87%8C%2C%E7%94%A8%E6%88%B7%E6%89%AB%E7%A0%81%E6%94%AF%E4%BB%98%20-----%E8%B7%B3%E8%BD%AC%E6%A8%A1%E5%BC%8F%20%E5%89%8D%E7%BD%AE%E6%A8%A1%E5%BC%8F%E7%9A%84%E6%94%AF%E4%BB%98%E5%AE%9E%E7%8E%B0%20%E6%AD%A5%E9%AA%A4%20%E9%80%9A%E8%BF%87%E5%90%8E%E7%AB%AF%E6%8F%90%E4%BE%9B%E7%9A%84%E6%8E%A5%E5%8F%A3%E8%8E%B7%E5%8F%96%E6%94%AF%E4%BB%98%E5%AE%9D%E7%9A%84%E6%95%B0%E6%8D%AE,%E2%80%94%E8%B0%83%E7%94%A8%E6%8E%A5%E5%8F%A3%20%E8%8E%B7%E5%8F%96%E6%94%AF%E4%BB%98%E5%AE%9D%E7%9A%84form%E6%95%B0%E6%8D%AE%20%28%E6%9C%80%E5%A5%BD%E8%BF%94%E5%9B%9E%E4%B8%80%E4%B8%AA%E6%AD%A4%E8%AE%A2%E5%8D%95%E5%8F%B7%29%20%E4%BD%BF%E7%94%A8iframe%20%E5%B0%86form%E6%95%B0%E6%8D%AE%E6%94%BE%E5%9C%A8%E9%A1%B5%E9%9D%A2%E4%B8%8A%E2%80%94%E7%94%9F%E6%88%90%E4%BA%8C%E7%BB%B4%E7%A0%81%20%E7%94%A8%E6%88%B7%E6%89%AB%E7%A0%81%20%E5%B1%95%E7%A4%BA%E4%B9%8B%E5%90%8E%E5%B0%B1%E8%AE%BE%E5%AE%9A%E5%AE%9A%E6%97%B6%E5%99%A8%20%E8%AF%B7%E6%B1%82%E6%94%AF%E4%BB%98%E7%BB%93%E6%9E%9C----%E5%90%8E%E7%AB%AF%E5%87%BA%E4%B8%80%E4%B8%AA%E6%8E%A5%E5%8F%A3%E6%9F%A5%E8%AF%A2%E8%BF%99%E4%B8%AA%E6%94%AF%E4%BB%98%E7%BB%93%E6%9E%9C%E5%81%9A%E7%9B%B8%E5%BA%94%E5%A4%84%E7%90%86")
    public R<OrderVO> createOrder(@RequestBody OrderRequest orderRequest) {
        return R.ok(ordersService.createOrder(orderRequest));
    }

    @PostMapping("/notify/payMode/{payMode}")
    @Operation(hidden = true)
//    @Operation(summary = "回调接口, 用户支付成功后支付宝官方会调用的接口来改变订单状态，无需前端调用")
    public String payNotify(@PathVariable("payMode") Integer payMode, HttpServletRequest request) {
        return ordersService.payNotify(request, payMode);
    }

    @PutMapping("/refund/orderId/{orderId}")
    @Operation(summary = "订单退款")
    public R<Boolean> refundPay(@PathVariable("orderId") String orderId) {
        return R.ok(ordersService.refundPay(orderId));
    }
}
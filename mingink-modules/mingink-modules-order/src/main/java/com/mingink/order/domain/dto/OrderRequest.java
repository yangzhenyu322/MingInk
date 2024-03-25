package com.mingink.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * / @Author: ZenSheep
 * / @Date: 2024/3/21 21:43
 */
@Data
@Schema(description = "订单请求参数")
public class OrderRequest {
    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "商品ID")
    private String goodsId;

    @Schema(description = "支付方式，0-微信、1-支付宝")
    private Integer payMode;

    @Schema(description = "返回路径，支付后前端跳转 url 链接，要求该前端页面能够被公网访问，开发过程可以使用netapp内网穿透，例如  http://zensheep.natapp1.cc/dashboard/workbench，zensheep.natapp1.cc表示访问域名，/dashboard/workbench表示前端页面路由")
    private String returnUrl;
}
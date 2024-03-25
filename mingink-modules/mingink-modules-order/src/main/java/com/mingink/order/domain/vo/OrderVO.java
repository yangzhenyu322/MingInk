package com.mingink.order.domain.vo;

import com.mingink.order.domain.entity.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/22 22:34
 */
@Data
@Schema(description = "订单VO对象")
public class OrderVO {
    @Schema(description = "订单")
    private Orders order;

    @Schema(description = "支付页面")
    private String formPage;
}

package com.mingink.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/21 20:03
 */
@Data
@Schema(description = "商品请求参数")
public class GoodsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品标题，如VIP")
    private String subject;

    @Schema(description = "商品金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00")
    private BigDecimal amount;

    @Schema(description = "库存量, -1表示不限量")
    private Long inventory;
}

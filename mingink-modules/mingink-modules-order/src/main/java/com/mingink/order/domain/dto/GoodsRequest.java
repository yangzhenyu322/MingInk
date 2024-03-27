package com.mingink.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/21 20:03
 */
@Data
@Schema(description = "商品请求参数")
public class GoodsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品标题，如VIP")
    private String subject;

    @Schema(description = "商品金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00")
    private BigDecimal amount;

    @Schema(description = "库存量, -1表示不限量")
    private Long inventory;

    @Schema(description = "商品权限，购买后即授予响应role权限")
    private Long roleId;

    @Schema(description = "有效时长,即用户购买后持有商品的时长,单位分钟,-1表示购买即永久持有")
    private Integer effectiveDuration;
}

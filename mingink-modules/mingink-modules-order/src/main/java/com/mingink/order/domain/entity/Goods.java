package com.mingink.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
@Data
@TableName("goods")
@Schema(description = "商品")
public class Goods implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    @Schema(description = "商品ID")
    private String id;

    @Schema(description = "商品标题，如VIP")
    private String subject;

    @Schema(description = "商品总金额，单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00")
    private BigDecimal amount;

    @Schema(description = "库存量, -1表示不限量")
    private Long inventory;

    @Schema(description = "商品权限，购买后即授予响应role权限")
    private Long roleId;

    @Schema(description = "有效时长,即用户购买后持有商品的时长,单位分钟,-1表示购买即永久持有")
    private Integer effectiveDuration;

    @Schema(description = "商品状态,0-上架/创建、1-告罄、2-下架")
    private Integer status;

    @Schema(description = "商品创建时间")
    private LocalDateTime createTime;

    @Schema(description = "商品告罄时间")
    private LocalDateTime exhaustedTime;

    @Schema(description = "商品下架时间")
    private LocalDateTime takeDownTime;
}

package com.mingink.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/22 22:24
 */
@SuppressWarnings("all")
@Data
@Schema(description = "支付请求参数")
public class PayParams {
    private static final long serialVersionUID = 1L;

    @Schema(description = "支付方式，0-微信、1-支付宝")
    private Integer payMode;

    @Schema(description = "商户订单号（必填），由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。如20150320010101001")
    private String tradeNo;

    @Schema(description = "订单总金额（必填），单位为元，精确到小数点后两位，取值范围：[0.01,100000000]，如9.00 ")
    private BigDecimal totalAmount;

    @Schema(description = "订单标题（必填），如Iphone6 16G")
    private String subject;

    @Schema(description = "订单超时时间")
    private LocalDateTime expireTime;

    @Schema(description = "返回路径，支付后前端跳转 url 链接，要求该前端页面能够被公网访问，开发过程可以使用netapp内网穿透，例如  http://zensheep.natapp1.cc/dashboard/workbench，zensheep.natapp1.cc表示访问域名，/dashboard/workbench表示前端页面路由")
    private String returnUrl;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商户订单号")
    private String outTradeNo;

    @Schema(description = "交易结束时间")
    private String timeExpire;

    @Schema(description = "附加数据")
    private String attach;

    @Schema(description = "订单优惠标记")
    private String goodsTag;

    @Schema(description = "电子发票入口开放标识")
    private Boolean supportFapiao;

    @Schema(description = "订单金额")
    private Amount amount;

    @Schema(description = "支付者")
    private Payer payer;

    @Schema(description = "优惠功能")
    private Detail detail;

    @Schema(description = "场景信息")
    private SceneInfo sceneInfo;

    @Schema(description = "结算信息")
    private SettleInfo settleInfo;


    /**
     * 订单金额
     */
    @Data
    public class Amount {
        // 总金额，单位为分
        private Integer total;
        // 货币类型
        private String currency;
    }

    /**
     * 支付者
     */
    @Data
    public class Payer {
        // 用户标识
        private String openId;
    }

    /**
     * 优惠功能，选填
     */
    @Data
    public class Detail {
        // 订单原价
        private Integer costPrice;
        // 商品小票ID
        private String invoiceId;
        // 单品列表
        private GoodsDetail[] goodsDetails;

        /**
         * 单品列表
         */
        @Data
        public class GoodsDetail {
            // 商户侧商品编码
            private String merchantGoodsId;
            // 微信支付商品编码
            private String wechatpayGoodsId;
            // 商品名称
            private String goodsName;
            // 商品数量
            private Integer quantity;
            // 商品单价
            private Integer unitPrice;
        }
    }

    /**
     * 场景信息，选填
     */
    @Data
    public class SceneInfo {
        // 用户终端IP
        private String payerClientIp;
        // 商户端设备号
        private String deviceId;
        // 商户门店信息
        private StoreInfo storeInfo;

        /**
         * 商户门店信息，选填
         */
        @Data
        public class StoreInfo {
            // 门店编号
            private String id;
            // 门店名称
            private String name;
            // 地区编码
            private String areaCode;
            // 详细地址
            private String address;
        }
    }

    /**
     * 结算信息
     */
    @Data
    public class SettleInfo {
        // 是否指定分账
        private Boolean profitSharing;
    }

}
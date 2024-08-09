package com.wszqj.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName OrderDetailVO
 * @description: TODO
 * @date 2024年08月05日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "OrderDetailVO",description = "订单详情")
public class OrderDetailVO {
    /** 订单编号 */
    @Schema(name = "id",description = "订单编号")
    private String id;

    /** 订单状态，1为待付款、2为待发货、3为待收货、4为待评价、5为已完成、6为已取消 */
    @Schema(name = "orderState",description = "订单状态")
    private Integer orderState;

    /** 倒计时--剩余的秒数 -1 表示已经超时，正数表示倒计时未结束 */
    @Schema(name = "countdown",description = "倒计时")
    private long countdown;

    /** 商品集合 [ 商品信息 ] */
    @Schema(name = "skus",description = "商品集合")
    private List<OrderSkuItem> skus;

    /** 收货人 */
    @Schema(name = "receiverContact",description = "收货人")
    private String receiverContact;

    /** 收货人手机 */
    @Schema(name = "receiverMobile",description = "收货人手机")
    private String receiverMobile;

    /** 收货人完整地址 */
    @Schema(name = "receiverAddress",description = "收货人完整地址")
    private String receiverAddress;

    /** 下单时间 */
    @Schema(name = "createTime",description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 商品总价 */
    @Schema(name = "totalMoney",description = "商品总价")
    private BigDecimal totalMoney;

    /** 运费 */
    @Schema(name = "postFee",description = "运费")
    private BigDecimal postFee;

    /** 应付金额 */
    @Schema(name = "payMoney",description = "应付金额")
    private BigDecimal payMoney;

    /**
     * 商品信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(name = "OrderSkuItem",description = "商品数据")
    public static class OrderSkuItem {
        /** sku id */
        @Schema(name = "id",description = "sku id")
        private Integer id;

        /** 商品 id */
        @Schema(name = "spuId",description = "商品")
        private Integer spuId;

        /** 商品名称 */
        @Schema(name = "name",description = "商品名称")
        private String name;

        /** 商品属性文字 */
        @Schema(name = "attrsText",description = "商品属性文字")
        private String attrsText;

        /** 数量 */
        @Schema(name = "count",description = "数量")
        private Integer count;

        /** 购买时单价 */
        @Schema(name = "curPrice",description = "购买时单价")
        private BigDecimal curPrice;

        /** 图片地址 */
        @Schema(name = "image",description = "图片地址")
        private String image;
    }
}
package com.wszqj.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName OrderDTO
 * @description: TODO
 * @date 2024年08月04日
 * @author: wszqj
 * @version: 1.0
 */
@Schema(name = "OrderDTO", description = "接受前端订单数据")
@Data
public class OrderDTO {
    // 订单收货地址ID
    @Schema(name = "addressId", description = "订单收货地址ID")
    private Integer addressId;
    // 订单备注
    @Schema(name = "buyMsg", description = "订单备注")
    private String buyMsg;
    // 配送时间段
    @Schema(name = "deliveryTimeType", description = "配送时间段")
    private Integer deliveryTimeType;
    // 支付渠道
    @Schema(name = "payChannel", description = "支付渠道")
    private Integer payChannel;
    // 支付方式
    @Schema(name = "payType", description = "支付方式")
    private Integer payType;
    // 商品数据
    @Schema(name = "Goods", description = "商品数据")
    private List<Goods> goods;

    @Data
    @Schema(name = "Goods", description = "商品数据")
    public static class Goods {
        @Schema(name = "skuId", description = "商品skuId")
        private String skuId;
        @Schema(name = "count", description = "商品数量")
        private Integer count;
    }
}

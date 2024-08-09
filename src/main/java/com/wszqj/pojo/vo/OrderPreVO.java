package com.wszqj.pojo.vo;

import com.wszqj.pojo.entry.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderPreVO
 * @description: 预付订单 返回信息
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPreVO {

    /** 商品集合 [ 商品信息 ] */
    private List<OrderPreGoods> goods;

    /** 结算信息 */
    private Summary summary;

    /** 用户地址列表 [ 地址信息 ] */
    private List<DeliveryAddress> userAddresses;

    /**
     * 结算信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Summary {
        /** 商品总价 */
        private BigDecimal totalPrice;

        /** 邮费 */
        private BigDecimal postFee;

        /** 应付金额 */
        private BigDecimal totalPayPrice;
    }

    /**
     * 商品信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderPreGoods {
        private String id; // 商品ID
        private String name; // 商品名称
        private String image; // 商品图片
        private String attrsText; // 商品sku属性
        private String skuId; // 商品skuId
        private BigDecimal price; // 商品单价
        private BigDecimal payPrice; // 商品实付单价
        private int count; // 商品数量
    }
}

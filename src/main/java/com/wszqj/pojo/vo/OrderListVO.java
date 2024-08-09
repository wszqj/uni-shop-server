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
 * @ClassName OrderListVO
 * @description: TODO
 * @date 2024年08月05日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OrderListVO",description = "订单列表")
public class OrderListVO {
    @Schema(name = "total",description = "总记录数")
    private Integer total;
    @Schema(name = "page",description = "当前页码")
    private Integer page;
    @Schema(name = "pages",description = "总页数")
    private Integer pages;
    @Schema(name = "pageSize",description = "页尺寸")
    private Integer pageSize;
    @Schema(name = "orderItems",description = "数据集合")
    private List<OrderItem> orderItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(name = "OrderItem",description = "订单列表元素")
    public static class OrderItem{
        @Schema(name = "orderId",description = "订单ID")
        private String orderId;
        @Schema(name = "skuId",description = "skuId")
        private Integer skuId;
        @Schema(name = "skuName",description = "sku名称")
        private String skuName;
        @Schema(name = "skuImg",description = "sku图片")
        private String skuImg;
        @Schema(name = "skuPrice",description = "sku价格")
        private BigDecimal skuPrice;
        @Schema(name = "skuAttrsText",description = "sku规格描述")
        private String skuAttrsText;
        @Schema(name = "count",description = "购买数量")
        private Integer count;
        @Schema(name = "createAt",description = "订单创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createAt;
        @Schema(name = "orderState",description = "订单状态")
        private Integer orderState;
        @Schema(name = "totalPrice",description = "总价")
        private BigDecimal totalPrice;
    }
}

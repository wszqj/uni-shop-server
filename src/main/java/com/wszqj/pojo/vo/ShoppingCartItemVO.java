package com.wszqj.pojo.vo;

import com.wszqj.pojo.entry.Discount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName ShoppingCartItemVO
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ShoppingCartItemVO", description = "购物车列表元素")
public class ShoppingCartItemVO {
    @Schema(name = "itemId", description = "元素ID")
    private Integer itemId;

    @Schema(name = "goodsId", description = "商品ID")
    private Integer goodsId;

    @Schema(name = "itemSkuId", description = "商品skuID")
    private Integer itemSkuId;

    @Schema(name = "itemAttributeMsg", description = "商品规格信息")
    private String itemAttributeMsg;

    @Schema(name = "goodsName", description = "商品名称")
    private String goodsName;

    @Schema(name = "itemImg", description = "商品图片")
    private String itemImg;

    @Schema(name = "itemPrice", description = "商品单价")
    private BigDecimal itemPrice;

    @Schema(name = "itemCount", description = "购买数量")
    private Integer itemCount;

    @Schema(name = "itemStock", description = "商品库存")
    private Integer itemStock;

    @Schema(name = "goodsDesc", description = "商品描述")
    private String goodsDesc;

    @Schema(name = "goodsDiscount", description = "折扣信息")
    private Discount goodsDiscount;

    @Schema(name = "itemStatus", description = "是否被选中")
    private Boolean itemStatus;
}

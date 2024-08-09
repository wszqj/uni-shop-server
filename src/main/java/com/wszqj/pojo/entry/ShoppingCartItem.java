package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 购物车项实体类
 */
@TableName(value = "shopping_cart_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItem {

    /**
     * 购物车项主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 购物车ID
     */
    private Integer cartId;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 商品skuID
     */
    private Integer goodsSkuId;

    /**
     * 购买数量
     */
    private Integer count;

    /**
     * 是否被选中 (0 = 未选中, 1 = 选中)
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;
}

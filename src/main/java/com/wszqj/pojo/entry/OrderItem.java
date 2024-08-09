package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName OrderItem
 * @description: 订单项
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("order_items")
public class OrderItem {

    // 订单项唯一标识符
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 关联的订单ID
    private String orderId;

    // 商品的唯一标识符
    private Integer skuId;

    // 商品数量
    private Integer count;
}
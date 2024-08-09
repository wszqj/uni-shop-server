package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Sku
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("skus")
public class Sku {

    // 主键，自增
    @TableId(type = IdType.AUTO)//设置主键的自增策略为主键自增（mybatisPlus默认为雪花算法）
    private Integer id;

    // 外键，指向Products表，不能为空
    private Integer goodsId;

    // 商品名称
    private String skuName;

    // SKU属性 (如颜色:红色, 尺码)，JSON格式，不能为空
    private String attributes;

    // 价格，不能为空
    private BigDecimal price;

    // 库存，不能为空
    private Integer stock;

    // 创建时间，默认当前时间
    private LocalDateTime createdAt;

    // 更新时间，默认当前时间
    private LocalDateTime updatedAt;

    // 样图，可为空
    private String pictures;
}






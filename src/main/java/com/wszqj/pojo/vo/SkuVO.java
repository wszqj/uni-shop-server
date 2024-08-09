package com.wszqj.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName SkuVO
 * @description: TODO
 * @date 2024年07月26日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuVO {
    // 主键
    private Integer id;
    // 外键
    private Integer goodsId;
    // SKU属性值列表
    private List<String> attributes;
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

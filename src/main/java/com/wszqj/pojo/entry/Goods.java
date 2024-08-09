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
 * @ClassName Goods
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("goods") // 指定该实体类映射的数据库表名
public class Goods {

    @TableId(type = IdType.AUTO) // 主键自增
    private Integer id;

    // 商品名称，不能为空
    private String name;

    // 商品描述，可为空
    private String description;

    // 主图图片集合，JSON格式，可为空
    private String mainVideos;

    // 主图视频比例，1为1:1/16:9，2为3:4，不能为空
    private Byte videoScale;

    // 商品类别，外键，指向Categories表，可为空
    private Integer categoryId;

    // 品牌，外键，指向Brands表，可为空
    private Integer brandId;

    // 当前价格，使用Decimal类型，精度为10,2
    private BigDecimal price;

    // 原价，使用Decimal类型，精度为10,2
    private BigDecimal oldPrice;

    // 折扣信息，外键，指向Discounts表，可为空
    private Integer discountId;

    // 属性详情，JSON格式，可为空
    private String details;

    // 创建时间，默认当前时间，不能为空
    private LocalDateTime createdAt;

    // 修改时间，默认当前时间，更新时自动设置，不能为空
    private LocalDateTime updatedAt;

    // 产品状态，不能为空，长度为5
    private String status;
}

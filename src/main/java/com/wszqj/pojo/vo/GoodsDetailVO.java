package com.wszqj.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wszqj.pojo.entry.Brand;
import com.wszqj.pojo.entry.Category;
import com.wszqj.pojo.entry.Discount;
import com.wszqj.pojo.entry.Sku;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @ClassName GoodsDeatilVO
 * @description: TODO
 * @date 2024年07月26日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDetailVO {
    // id
    private Integer id;
    // 商品名称
    private String name;
    // 商品描述
    private String description;
    // 主图图片集合
    private List<String> mainVideos;
    // 主图视频比例
    private Byte videoScale;
    // 当前价格
    private BigDecimal price;
    // 原价
    private BigDecimal oldPrice;
    // 属性详情
    private List<Detail> details;
    // 创建时间
    private LocalDateTime createdAt;
    // 产品状态
    private String status;
    // 商品类别
    private Category category;
    // 品牌
    private Brand brand;
    // 折扣信息
    private Discount discount;
    // sku集合
    private List<SkuVO> skus;
    // 属性集合
    private List<Spec> specs;


    // 属性对象
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Spec {
        // 属性名
        private String name;
        // 属性列表
        private Set<Attribute> attributes;
    }


    // 属性值对象
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Attribute {
        // 属性值
        private String name;
    }

    // 属性详情
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Detail {
        private String name;
        private String value;
    }
}

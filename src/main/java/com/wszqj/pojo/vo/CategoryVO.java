package com.wszqj.pojo.vo;

/**
 * @ClassName CategoryVO
 * @description: TODO
 * @date 2024年08月07日
 * @author: wszqj
 * @version: 1.0
 */

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 一级分类对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "分类视图对象")
public class CategoryVO  {

    @Schema(name = "id" ,description = "一级分类id")
    private Integer id;

    @Schema(name = "name" ,description = "一级分类名称")
    private String name;

    @Schema(name = "picture" ,description = "一级分类图片")
    private String picture;

    @Schema(name = "children" ,description = "二级分类集合")
    private List<SecondaryCategory> children;

    /**
     * 二级分类对象
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(name = "SecondaryCategory",description = "二级分类对象")
    public static class SecondaryCategory {

        @Schema(name = "id" ,description = "二级分类id")
        private Integer id;

        @Schema(name = "name" ,description = "二级分类名称")
        private String name;

        @Schema(name = "picture" ,description = "二级分类图片")
        private String picture;

        @Schema(name = "parentId" ,description = "父级分类id")
        private Integer parentId;

        @Schema(name = "parentName" ,description = "父级分类名称")
        private String parentName;

        @Schema(name = "goods" ,description = "商品集合")
        private List<Goods> goods;

        /**
         * 商品对象
         */
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Goods {
            @Schema(name = "id" ,description = "商品id")
            private Integer id;

            @Schema(name = "name" ,description = "商品名称")
            private String name;

            @Schema(name = "desc" ,description = "商品描述")
            private String desc;

            @Schema(name = "price" ,description = "商品价格")
            private String price;

            @Schema(name = "picture" ,description = "商品图片")
            private String picture;
        }
    }
}

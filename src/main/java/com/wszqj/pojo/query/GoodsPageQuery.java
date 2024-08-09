package com.wszqj.pojo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GoodsQuery
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "GoodsPageQuery",description = "分页查询对象")
public class GoodsPageQuery {
    @Schema(name = "page",description = "第几页页数")
    private int page;

    @Schema(name = "pageSize",description = "每页数据量")
    private int pageSize;

    @Schema(name = "name",description = "商品名")
    private String name;
}

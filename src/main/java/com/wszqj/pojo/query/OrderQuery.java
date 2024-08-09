package com.wszqj.pojo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OrderQuery
 * @description: TODO
 * @date 2024年08月05日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@Schema(name = "OrderQuery",description = "订单分页查询对象")
public class OrderQuery {
    @Schema(name = "page",description = "第几页页数")
    private Integer page;

    @Schema(name = "pageSize",description = "每页数据量")
    private Integer pageSize;

    @Schema(name = "status",description = "订单状态")
    private Integer orderState;
}

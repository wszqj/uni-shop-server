package com.wszqj.pojo.dto;

import com.wszqj.pojo.entry.Discount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName ShoppingCartItemDTO
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Data
public class ShoppingCartItemDTO {
    @Schema(name = "itemId", description = "元素ID")
    private Integer itemId;

    @Schema(name = "itemCount", description = "购买数量")
    private Integer itemCount;

    @Schema(name = "itemStatus", description = "是否被选中")
    private Boolean itemStatus;
}

package com.wszqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.ShoppingCartItemMapper;
import com.wszqj.pojo.dto.ShoppingCartItemDTO;
import com.wszqj.pojo.entry.ShoppingCartItem;
import com.wszqj.pojo.result.Result;
import com.wszqj.service.IShoppingCartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @ClassName IShoppingCartItemServiceImpl
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class IShoppingCartItemServiceImpl extends ServiceImpl<ShoppingCartItemMapper, ShoppingCartItem> implements IShoppingCartItemService {
    private final ShoppingCartItemMapper shoppingCartItemMapper;

    /**
     * 修改购物车单品状态
     *
     * @param shoppingCartItemDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    public Result<Void> updateShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {
        // 构造shoppingCartItem
        shoppingCartItemMapper.updateById(ShoppingCartItem.builder()
                .id(shoppingCartItemDTO.getItemId())
                .status(shoppingCartItemDTO.getItemStatus() ? (byte) 1 : (byte) 0)
                .count(shoppingCartItemDTO.getItemCount())
                .build());
        return Result.success("更新成功");
    }
}

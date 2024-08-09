package com.wszqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wszqj.pojo.dto.ShoppingCartItemDTO;
import com.wszqj.pojo.entry.ShoppingCartItem;
import com.wszqj.pojo.result.Result;

/**
 * @ClassName IShoppingCartItemService
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
public interface IShoppingCartItemService extends IService<ShoppingCartItem> {
    /**
     *  修改购物车单品状态
     * @param shoppingCartItemDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> updateShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO);
}

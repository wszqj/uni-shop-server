package com.wszqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wszqj.pojo.entry.ShoppingCart;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.ShoppingCartItemVO;

import java.util.List;

/**
 * @ClassName IShoppingCartService
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    /**
     *  获取购物车列表
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.ShoppingCartItemVO>
     **/
    Result<List<ShoppingCartItemVO>> getShoppingCart();

    /**
     *  修改全选状态
     * @param status
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> updateCartAllChecked(Boolean status);

    /**
     *  添加购物车
     * @param skuId
     * @param count
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> addShoppingCartItem(String skuId, Integer count);
}

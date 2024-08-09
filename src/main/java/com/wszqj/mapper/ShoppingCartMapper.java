package com.wszqj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wszqj.pojo.entry.ShoppingCart;
import com.wszqj.pojo.entry.ShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName ShoppingCartMapper
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    @Select("select id, goods_id, count, status, goods_sku_id from wszqj_get_money.shopping_cart_item where cart_id =#{cartId}")
    List<ShoppingCartItem> selectedItemList(@Param("cartId") Integer id);
}

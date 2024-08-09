package com.wszqj.controller;

import com.wszqj.mapper.ShoppingCartItemMapper;
import com.wszqj.pojo.dto.ShoppingCartItemDTO;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.ShoppingCartItemVO;
import com.wszqj.service.IShoppingCartItemService;
import com.wszqj.service.IShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ShoppingCartController
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "购物车相关接口")
public class ShoppingCartController {

    private final IShoppingCartService shoppingCartService;
    private final IShoppingCartItemService shoppingCartItemService;
    private final ShoppingCartItemMapper shoppingCartItemMapper;

    /**
     * 获取购物车列表
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.ShoppingCartItemVO>
     **/
    @Operation(summary = "获取购物车列表")
    @GetMapping("/list")
    public Result<List<ShoppingCartItemVO>> getShoppingCart() {
        log.info("获取购物车列表");
        return shoppingCartService.getShoppingCart();
    }

    /**
     * 修改购物车单品状态
     *
     * @param shoppingCartItemDTO 购物车商品元素
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "修改购物车单品状态")
    @PutMapping("/update")
    public Result<Void> updateShoppingCartItem(@RequestBody ShoppingCartItemDTO shoppingCartItemDTO) {
        log.info("修改购物车单品状态：{}", shoppingCartItemDTO);
        return shoppingCartItemService.updateShoppingCartItem(shoppingCartItemDTO);
    }


    /**
     * 删除购物车中的商品
     *
     * @param id 购物车商品元素id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "删除购物车中的商品")
    @DeleteMapping("/delete")
    public Result<Void> deleteShoppingCartItem(@RequestParam("id") Integer id) {
        log.info("删除购物车中的商品：{}", id);
        int i = shoppingCartItemMapper.deleteById(id);
        return i > 0 ? Result.success("删除成功") : Result.error("删除失败");
    }


    /**
     * 修改全选状态
     *
     * @param status 购物车商品元素状态
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "修改全选状态")
    @PutMapping("/updateAllChecked")
    public Result<Void> updateCartAllChecked(@RequestParam("status") Boolean status) {
        log.info("修改全选状态：{}", status);
        return shoppingCartService.updateCartAllChecked(status);
    }

    /**
     * 添加购物车
     *
     * @param skuId 购物车商品元素skuId
     * @param count 购物车商品元素数量
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "添加购物车商品")
    @PutMapping("/add")
    public Result<Void> addShoppingCartItem(@RequestParam("skuId") String skuId, @RequestParam("count") Integer count) {
        log.info("添加购物车：skuId：{}，count：{}", skuId, count);
        return shoppingCartService.addShoppingCartItem(skuId, count);
    }
}

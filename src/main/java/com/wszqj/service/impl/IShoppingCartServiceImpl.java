package com.wszqj.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.*;
import com.wszqj.pojo.entry.*;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.ShoppingCartItemVO;
import com.wszqj.service.IShoppingCartItemService;
import com.wszqj.service.IShoppingCartService;
import com.wszqj.utils.JsonUtils; // 假设你将 parseJsonToFormattedString 移动到了 JsonUtils 工具类
import com.wszqj.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName IShoppingCartServiceImpl
 * @description: 购物车服务实现类
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartItemMapper shoppingCartItemMapper;
    private final IShoppingCartItemService shoppingCartItemService;
    private final GoodsMapper goodsMapper;
    private final DiscountMapper discountMapper;
    private final SkuMapper skuMapper;

    /**
     * 获取购物车列表
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.ShoppingCartItemVO>
     **/
    @Override
    public Result<List<ShoppingCartItemVO>> getShoppingCart() {
        // 购物车元素列表
        List<ShoppingCartItemVO> list = new ArrayList<>();
        // 获取当前操作用户
        Integer userId = ThreadLocalUtil.getUserId();
        // 获取当前操作用户购物车
        ShoppingCart shoppingCart = lambdaQuery().eq(ShoppingCart::getUserId, userId).one();
        if (shoppingCart == null) {
            log.warn("购物车为空, 用户ID: {}", userId);
            return Result.success(Collections.emptyList());
        }
        // 根据购物车ID获取当前购物车中所有的单品
        List<ShoppingCartItem> itemList = shoppingCartMapper.selectedItemList(shoppingCart.getId());
        if (itemList.isEmpty()) {
            log.info("购物车没有商品, 用户ID: {}", userId);
            return Result.success(Collections.emptyList());
        }
        // 批量获取商品、SKU 和折扣信息
        Set<Integer> goodsIds = itemList.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toSet());
        Set<Integer> skuIds = itemList.stream().map(ShoppingCartItem::getGoodsSkuId).collect(Collectors.toSet());

        // 批量获取商品
        List<Goods> goodsList = goodsMapper.selectBatchIds(goodsIds);
        Map<Integer, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getId, goods -> goods));
        // 批量获取SKU
        List<Sku> skuList = skuMapper.selectBatchIds(skuIds);
        Map<Integer, Sku> skuMap = skuList.stream().collect(Collectors.toMap(Sku::getId, sku -> sku));
        // 批量获取折扣信息
        List<Discount> discountList = discountMapper.selectBatchIds(
                goodsList.stream().map(Goods::getDiscountId).collect(Collectors.toSet())
        );
        Map<Integer, Discount> discountMap = discountList.stream().collect(Collectors.toMap(Discount::getId, discount -> discount));

        // 封装集合
        for (ShoppingCartItem item : itemList) {
            Goods goods = goodsMap.get(item.getGoodsId());
            Sku sku = skuMap.get(item.getGoodsSkuId());
            Discount discount = discountMap.getOrDefault(goods.getDiscountId(), new Discount());
            // 健壮性判断
            if (sku == null) {
                log.error("商品或SKU信息丢失, 商品ID: {}, SKU ID: {}", item.getGoodsId(), item.getGoodsSkuId());
                continue;
            }
            // 解析sku属性信息
            String attributeString = JsonUtils.parseJsonToFormattedString(sku.getAttributes());
            // 封装 ShoppingCartItemVO
            list.add(
                    ShoppingCartItemVO.builder()
                            .itemId(item.getId())
                            .goodsId(goods.getId())
                            .itemSkuId(sku.getId())
                            .itemAttributeMsg(attributeString)
                            .goodsName(goods.getName())
                            .itemImg(sku.getPictures())
                            .itemPrice(sku.getPrice())
                            .itemCount(item.getCount())
                            .itemStock(sku.getStock())
                            .goodsDesc(goods.getDescription())
                            .goodsDiscount(discount)
                            .itemStatus(item.getStatus() == 1)
                            .build()
            );
        }
        return Result.success(list);
    }

    /**
     * 修改购物车中所有商品的全选状态
     *
     * @param status 是否全选状态。为 true 表示全选，false 表示取消全选。
     * @return com.wszqj.pojo.result.Result<java.lang.Void> 操作结果，包含成功或失败的信息。
     **/
    @Override
    public Result<Void> updateCartAllChecked(Boolean status) {
        // 创建 LambdaUpdateWrapper 实例，用于构建更新条件
        LambdaUpdateWrapper<ShoppingCartItem> updateWrapper = new LambdaUpdateWrapper<>();

        // 设置更新条件：
        // 这里根据传入的 status 参数决定 status 字段的值
        updateWrapper.set(ShoppingCartItem::getStatus, status ? 1 : 0);

        // 执行更新操作：
        // updateWrapper 中的条件为空，表示将应用于所有记录
        // 第一个参数为 null，意味着没有设置具体的查询条件，所有记录都会被更新
        // 第二个参数是 updateWrapper，其中包含要更新的字段和新值
        shoppingCartItemMapper.update(null, updateWrapper);
        return Result.success("操作成功");
    }


    /**
     * 添加购物车
     *
     * @param skuId
     * @param count
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    public Result<Void> addShoppingCartItem(String skuId, Integer count) {
        // 根据skuID查询该sku所属商品
        Sku sku = skuMapper.selectById(skuId);
        if (sku == null) {
            return Result.error("不存在该商品组合");
        }
        // 获取当前操作用户
        Integer userId = ThreadLocalUtil.getUserId();
        // 查询当前操作用户的购物车ID
        ShoppingCart shoppingCart = lambdaQuery()
                .eq(ShoppingCart::getUserId, userId).one();
        if (shoppingCart == null) {
            return Result.error("添加异常");
        }
        // 根据skuID查询 shoppingCartItem
        ShoppingCartItem shoppingCartItem = shoppingCartItemService.lambdaQuery()
                .eq(ShoppingCartItem::getGoodsSkuId, skuId).one();
        // 判断购物车中是否已经存在该商品
        if (shoppingCartItem != null) {
            // 已存在 构造 ShoppingCartItem 并更新数据库
            shoppingCartItemMapper.updateById(
                    ShoppingCartItem.builder()
                            .id(shoppingCartItem.getId())
                            .goodsId(shoppingCartItem.getGoodsId())
                            .goodsSkuId(shoppingCartItem.getGoodsSkuId())
                            .cartId(shoppingCartItem.getCartId())
                            .count(shoppingCartItem.getCount() + count)
                            .build());
            return Result.success("添加成功，记得查看哦√");
        }
        // 不存在 构造ShoppingCartItem 并添加数据库
        shoppingCartItemMapper.insert(ShoppingCartItem.builder()
                .goodsId(sku.getGoodsId())
                .goodsSkuId(sku.getId())
                .cartId(shoppingCart.getId())
                .count(count)
                .build());
        return Result.success("添加成功，记得查看哦√");
    }
}

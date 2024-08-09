package com.wszqj.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.constant.OrderConstant;
import com.wszqj.constant.ResultConstant;
import com.wszqj.exception.OrderException;
import com.wszqj.mapper.*;
import com.wszqj.pojo.dto.OrderDTO;
import com.wszqj.pojo.entry.*;
import com.wszqj.pojo.query.OrderQuery;
import com.wszqj.pojo.result.PageResult;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.OrderDetailVO;
import com.wszqj.pojo.vo.OrderListVO;
import com.wszqj.pojo.vo.OrderPreVO;
import com.wszqj.service.*;
import com.wszqj.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wszqj.constant.ShoppingCartItemConstant.IS_SELECTED;

/**
 * @ClassName IOrderServiceImpl
 * @description: TODO
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IOrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    private final IShoppingCartService shoppingCartService;
    private final IShoppingCartItemService shoppingCartItemService;
    private final IDeliveryAddressService deliveryAddressService;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final GoodsMapper goodsMapper;
    private final SkuMapper skuMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final IOrderItemService orderItemService;
    private final IOrderDetailsService orderDetailsService;


    /**
     * 获取预支付交易单
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    @Override
    public Result<OrderPreVO> getOrderPre() {
        // 获取当前操作用户地址列表
        List<DeliveryAddress> deliveryAddressList = getDeliveryAddressList();
        // 获取当前操作用户购物车
        ShoppingCart shoppingCart = shoppingCartService.lambdaQuery()
                .eq(ShoppingCart::getUserId, ThreadLocalUtil.getUserId()).one();
        // 获取购物车中选中的商品
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemService.lambdaQuery()
                .eq(ShoppingCartItem::getCartId, shoppingCart.getId())
                .eq(ShoppingCartItem::getStatus, IS_SELECTED)
                .list();
        // 提取skuId集合
        List<Integer> skuIds = new ArrayList<>();
        shoppingCartItems.forEach(shoppingCartItem -> skuIds.add(shoppingCartItem.getGoodsSkuId()));
        // 收集购物车商品数据
        Map<Integer, ShoppingCartItem> shoppingCartItemMap = shoppingCartItems.stream()
                .collect(Collectors.toMap(ShoppingCartItem::getGoodsSkuId, shoppingCartItem -> shoppingCartItem));
        // 批量获取sku集合
        List<Sku> skus = skuMapper.selectBatchIds(skuIds);
        // 收集sku数据
        Map<Integer, Sku> skuMap = skus.stream().collect(Collectors.toMap(Sku::getId, sku -> sku));
        // TODO 目前未算入优惠，后期优化
        // 构造 OrderPreVO.OrderPreGoods 集合
        List<OrderPreVO.OrderPreGoods> orderPreGoodsList = new ArrayList<>();
        skuMap.forEach((k, v) -> shoppingCartItemMap.forEach((kk, vv) -> {
            // 如果skuId相匹配
            if (k.equals(kk)) {
                // 获取skuMap当前sku
                Sku sku = skuMap.get(k);
                // 获取shoppingCartItemMap当前匹配ShoppingCartItem
                ShoppingCartItem item = shoppingCartItemMap.get(kk);
                // 将封装好的 OrderPreVO.OrderPreGoods 添加进 orderPreGoodsList
                orderPreGoodsList.add(
                        // 构造 OrderPreVO.OrderPreGoods 商品信息
                        getOrderPreGoods(sku, String.valueOf(item.getCount()))
                );
            }
        }));
        // 商品总价 (优惠前)
        double totalPrice = 0.00;
        // 商品总价 (优惠后)
        double totalPayPrice = 0.00;
        // 计算总价
        for (OrderPreVO.OrderPreGoods goods : orderPreGoodsList) {
            totalPrice += goods.getPrice().doubleValue() * goods.getCount();
            totalPayPrice += goods.getPayPrice().doubleValue() * goods.getCount();
        }
        // 构造 OrderPreVO.Summary 结算信息
        OrderPreVO.Summary summary = getSummary(totalPrice, totalPayPrice);
        // 封装返回信息
        return Result.success(
                OrderPreVO
                        .builder()
                        .goods(orderPreGoodsList)
                        .userAddresses(deliveryAddressList)
                        .summary(summary)
                        .build()
        );
    }


    /**
     * 立即购买，生成预支付订单
     *
     * @param skuId
     * @param count
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    @Override
    public Result<OrderPreVO> buyNow(String skuId, String count) {
        // 获取当前操作用户地址列表
        List<DeliveryAddress> deliveryAddressList = getDeliveryAddressList();
        // 根据当前skuID查询sku
        Sku sku = skuMapper.selectById(skuId);
        if (sku.getStock() <= Integer.parseInt(count)) {
            return Result.error("库存不足");
        }
        // 获取当前sku关联的商品
        Goods goods = goodsMapper.selectById(sku.getGoodsId());
        if (goods == null) {
            return Result.error("不存在该商品");
        }
        // 构造 OrderPreVO.OrderPreGoods 商品信息
        OrderPreVO.OrderPreGoods orderPreGoods = getOrderPreGoods(sku, count);
        // 商品总价 (优惠前)
        double totalPrice = sku.getPrice().doubleValue() * Integer.parseInt(count);
        // 商品总价 (优惠后)
        double totalPayPrice = sku.getPrice().doubleValue() * Integer.parseInt(count);
        // 构造 OrderPreVO.Summary结算信息
        OrderPreVO.Summary summary = getSummary(totalPrice, totalPayPrice);
        // 构造返回数据
        return Result.success(
                OrderPreVO.builder()
                        .goods(CollUtils.singletonList(orderPreGoods))
                        .userAddresses(deliveryAddressList)
                        .summary(summary)
                        .build()
        );
    }


    /**
     * 创建订单
     *
     * @param orderDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    @Transactional
    public Result<String> createOrder(OrderDTO orderDTO) {
        // 映射整体skuID
        List<String> skuIds = orderDTO.getGoods().stream()
                .map(OrderDTO.Goods::getSkuId)
                .toList();
        // 将 orderDTO 的商品集合 转换为 map
        Map<String, OrderDTO.Goods> goodsMap = orderDTO.getGoods().stream()
                .collect(Collectors.toMap(OrderDTO.Goods::getSkuId, goods -> goods));
        // 订单id集合
        List<String> ids = new ArrayList<>();
        // 遍历得到每一个goods分别创建订单
        for (String skuId : skuIds) {
            // 获取与当前skuId匹配的 goods
            OrderDTO.Goods goods = goodsMap.get(skuId);
            // 为每一个商品分别创建订单
            Result<String> result = created(skuId, goods, orderDTO);
            // 收集订单id
            ids.add(result.getResult());
            if (result.getCode().equals(ResultConstant.ERROR)) {
                // 业务逻辑中的错误，抛出异常以触发事务回滚
                throw new RuntimeException("部分订单创建失败: " + result.getMsg());
            }
        }
        return Result.success(String.join(",", ids), "创建成功");
    }

    /**
     * 获取指定订单列表
     *
     * @param orderQuery
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderListVO>
     **/
    @Override
    public Result<PageResult<OrderListVO.OrderItem>> orderList(OrderQuery orderQuery) {
        // 获取订单分页列表
        Page<Order> page = getOrderPage(orderQuery);
        // 订单元素列表
        List<Order> list = page.getRecords();
        List<OrderListVO.OrderItem> orderItems = new ArrayList<>(list.size());
        list.forEach(order -> {
            OrderItem orderItem = orderItemService.lambdaQuery()
                    .eq(OrderItem::getOrderId, order.getId()).one();
            // 获取当前关联sku
            Sku sku = skuMapper.selectById(orderItem.getSkuId());
            orderItems.add(
                    OrderListVO.OrderItem
                            .builder()
                            .orderId(order.getId())
                            .skuId(orderItem.getSkuId())
                            .skuName(sku.getSkuName())
                            .skuImg(sku.getPictures())
                            .skuPrice(sku.getPrice())
                            .count(orderItem.getCount())
                            .createAt(order.getCreateAt())
                            .orderState(order.getStatus())
                            .skuAttrsText(JsonUtils.parseJsonToFormattedString(sku.getAttributes()))
                            .totalPrice(sku.getPrice().multiply(BigDecimal.valueOf(orderItem.getCount()))
                                    .add(BigDecimal.valueOf(18)))
                            .build()
            );
        });
        // 封装返回数据
        PageResult<OrderListVO.OrderItem> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(orderItems);
        pageResult.setPage(page.getCurrent());
        pageResult.setPages(page.getPages());
        pageResult.setPageSize(page.getSize());
        return Result.success(pageResult);
    }

    /**
     * 获取订单详情
     *
     * @param id 订单id
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderDetailVO>
     **/
    @Override
    public Result<OrderDetailVO> getOrderDetail(String id) {
        // 获取当前查询订单详情信息
        OrderDetails details = orderDetailsService.lambdaQuery().eq(OrderDetails::getOrderId, id).one();
        // 获取当前订单地址信息
        DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(details.getAddressId());
        // 拼接收货人完整地址
        String receiverAddress = deliveryAddress.getFullLocation() +
                deliveryAddress.getAddress();
        // 查询sku
        Sku sku = skuMapper.selectById(details.getSkuId());
        // 分装返回数据 OrderDetailVO.OrderSkuItem
        OrderDetailVO.OrderSkuItem orderSkuItem = OrderDetailVO.OrderSkuItem.builder()
                .id(sku.getId())
                .spuId(details.getSpuId())
                .name(sku.getSkuName())
                .attrsText(JsonUtils.parseJsonToFormattedString(sku.getAttributes()))
                .count(details.getCount())
                .curPrice(sku.getPrice())
                .image(sku.getPictures())
                .build();
        // 计算距超时 还剩多少秒
        long timeOut = TimeUtils.TIME_MAX -
                TimeUtils.calculateSecondsBetween(details.getCreatedAt(), LocalDateTime.now());
        // 判断是否样机超时
        if (timeOut <= 0) {
            timeOut = -1;
        }
        // 封装 OrderDetailVO
        return Result.success(OrderDetailVO.builder()
                .id(id)
                .orderState(details.getOrderState())
                .countdown(timeOut)
                .skus(CollUtils.singletonList(orderSkuItem))
                .receiverContact(deliveryAddress.getConsignee())
                .receiverMobile(deliveryAddress.getPhone())
                .receiverAddress(receiverAddress)
                .createTime(details.getCreatedAt())
                .totalMoney(details.getTotalPrice())
                .postFee(details.getPostPrice())
                .payMoney(details.getTotalPayPrice().add(details.getPostPrice()))
                .build());
    }

    /**
     * 再次购买
     *
     * @param orderId
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    @Override
    public Result<OrderPreVO> getOrderPreAgain(String orderId) {
        // 获取订单详情
        OrderDetails details = orderDetailsService.lambdaQuery()
                .eq(OrderDetails::getOrderId, orderId).one();
        // 获取地址信息
        List<DeliveryAddress> deliveryAddressList = deliveryAddressService.lambdaQuery()
                .eq(DeliveryAddress::getUserId, ThreadLocalUtil.getUserId()).list();
        // 获取当前sku
        Sku sku = skuMapper.selectById(details.getSkuId());
        if (sku == null) {
            throw new OrderException("商品已下架");
        }
        if (details.getCount() > sku.getStock()) {
            throw new OrderException("库存不足");
        }
        // 封装商品信息
        OrderPreVO.OrderPreGoods orderPreGoods = OrderPreVO.OrderPreGoods
                .builder()
                .id(String.valueOf(details.getSpuId()))
                .name(details.getSkuName())
                .image(sku.getPictures())
                .attrsText(JsonUtils.parseJsonToFormattedString(sku.getAttributes()))
                .skuId(String.valueOf(sku.getId()))
                .price(sku.getPrice())
                .payPrice(sku.getPrice()) // 无优惠
                .count(details.getCount())
                .build();
        // 计算商品总价
        BigDecimal totalPrice = sku.getPrice().multiply(BigDecimal.valueOf(details.getCount()));
        BigDecimal totalPayPrice = sku.getPrice().multiply(BigDecimal.valueOf(details.getCount()));
        // 封装 summary
        OrderPreVO.Summary summary = getSummary(totalPrice.doubleValue(), totalPayPrice.doubleValue());
        // 构造返回数据
        return Result.success(
                OrderPreVO.builder()
                        .goods(CollUtils.singletonList(orderPreGoods))
                        .userAddresses(deliveryAddressList)
                        .summary(summary)
                        .build());
    }

    /**
     * 模拟订单支付
     *
     * @param id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Transactional
    @Override
    public Result<Void> payOrder(String id) {
        // 解析订单ID列表
        List<String> ids = List.of(id.split(","));
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 批量更新订单详情状态和支付时间
        boolean orderDetailsUpdated = orderDetailsService.lambdaUpdate()
                .in(OrderDetails::getOrderId, ids)
                .set(OrderDetails::getOrderState, OrderConstant.STATUS_DFH)
                .set(OrderDetails::getPayAt, now)
                .update();

        if (!orderDetailsUpdated) {
            throw new OrderException("更新订单详情状态失败");
        }
        // 批量更新订单状态
        boolean orderUpdated = lambdaUpdate()
                .in(Order::getId, ids)
                .set(Order::getStatus, OrderConstant.STATUS_DFH)
                .update();
        if (!orderUpdated) {
            throw new OrderException("更新订单状态失败");
        }
        return Result.success("支付成功");
    }

    /**
     * 删除订单
     *
     * @param id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Transactional
    @Override
    public Result<Void> deleteOrder(String id) {
        try {
            // 删除订单
            int deleteOrderResult = orderMapper.deleteById(id);
            if (deleteOrderResult <= 0) {
                throw new OrderException("订单信息删除失败");
            }

            // 删除订单内购买的商品数据
            boolean removeOrderItem = orderItemService.lambdaUpdate()
                    .eq(OrderItem::getOrderId, id)
                    .remove();
            if (!removeOrderItem) {
                throw new OrderException("删除订单内购买的商品数据失败");
            }

            // 删除订单详情
            boolean removeOrderDetails = orderDetailsService.lambdaUpdate()
                    .eq(OrderDetails::getOrderId, id)
                    .remove();
            if (!removeOrderDetails) {
                throw new OrderException("删除订单详情失败");
            }

            return Result.success("删除成功");
        } catch (Exception e) {
            // 捕获所有异常并处理
            throw new OrderException("删除订单过程中发生错误: " + e.getMessage());
        }
    }

    /**
     * 取消订单
     *
     * @param id
     * @param reason
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    @Transactional
    public Result<Void> cancelOrder(String id, String reason) {
        // 更新订单状态
        int i = orderMapper.updateById(Order.builder().id(id).status(OrderConstant.STATUS_YQX).build());
        if (i <= 0) {
            throw new OrderException("订单取消失败");
        }
        // 更新订单详情
        boolean update = orderDetailsService.lambdaUpdate()
                .eq(OrderDetails::getOrderId, id)
                .set(OrderDetails::getCancelReason,reason)
                .set(OrderDetails::getOrderState, OrderConstant.STATUS_YQX)
                .update();
        if (!update) {
            throw new OrderException("订单详情信息更新失败");
        }
        // 更新订单详情状态
        return Result.success("取消成功");
    }


    /**
     * 获取订单分页列表
     *
     * @param orderQuery
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.wszqj.pojo.entry.Order>
     **/
    public Page<Order> getOrderPage(OrderQuery orderQuery) {
        Page<Order> p = Page.of(orderQuery.getPage(), orderQuery.getPageSize());
        // 获取当前操作用户
        Integer userId = ThreadLocalUtil.getUserId();
        // 判断是否为全部订单
        if (orderQuery.getOrderState() != null && orderQuery.getOrderState() == 0) {
            // 全部订单
            return lambdaQuery()
                    .eq(userId != null, Order::getUserId, userId)
                    .orderByDesc(Order::getId).page(p);
        } else {
            // 部分订单
            return lambdaQuery()
                    .eq(orderQuery.getOrderState() != null, Order::getStatus, orderQuery.getOrderState())
                    .eq(userId != null, Order::getUserId, userId)
                    .orderByDesc(Order::getId).page(p);
        }
    }

    @Transactional
    public Result<String> created(String skuId, OrderDTO.Goods goods, OrderDTO orderDTO) {
        // 查询商品库存是否充足
        Sku sku = skuMapper.selectById(skuId);
        if (sku == null || sku.getStock() < goods.getCount()) {
            throw new OrderException("库存不足");
        }
        // 获取当前用户ID
        Integer userId = ThreadLocalUtil.getUserId();
        // 订单唯一ID
        String orderId = OrderIdGenerator.generateOrderId();
        // 创建订单
        Order order = Order.builder()
                .id(orderId)
                .userId(userId)
                .status(OrderConstant.STATUS_DFK)
                .build();
        int insertOrder = orderMapper.insert(order);
        if (JudgeUtils.lessZero(insertOrder)) {
            throw new OrderException("订单创建失败");
        }
        // 扣减库存
        Sku build = Sku.builder()
                .id(Integer.valueOf(skuId))
                .stock(sku.getStock() - goods.getCount())
                .build();
        skuMapper.updateById(build);
        // 存储订单商品信息
        OrderItem orderItem = OrderItem.builder()
                .orderId(orderId)
                .skuId(Integer.valueOf(skuId))
                .count(goods.getCount())
                .build();
        int insertOrderItem = orderItemMapper.insert(orderItem);
        if (JudgeUtils.lessZero(insertOrderItem)) {
            throw new OrderException("订单商品信息创建失败");
        }
        // 计算商品总价
        BigDecimal price = sku.getPrice();
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(goods.getCount()));
        BigDecimal totalPayPrice = price.multiply(BigDecimal.valueOf(goods.getCount())); // TODO 暂时没有计算优惠
        // 存储订单详情
        OrderDetails orderDetails = OrderDetails.builder()
                .orderId(orderId)
                .addressId(orderDTO.getAddressId())
                .spuId(sku.getGoodsId())
                .skuName(sku.getSkuName())
                .skuId(sku.getId())
                .count(goods.getCount())
                .buyPrice(sku.getPrice())
                .totalPrice(totalPrice)
                .totalPayPrice(totalPayPrice)
                .postPrice(BigDecimal.valueOf(18)) // 暂定运费
                .payType(OrderConstant.TYPE_WX)
                .buyerMsg(orderDTO.getBuyMsg())
                .orderState(OrderConstant.STATUS_DFK)
                .build();
        int insertOrderDetails = orderDetailsMapper.insert(orderDetails);
        if (JudgeUtils.lessZero(insertOrderDetails)) {
            throw new OrderException("订单详情创建失败");
        }
        return Result.success(orderId, "订单创建成功");
    }

    /**
     * 查询用户地址列表
     *
     * @return java.util.List<com.wszqj.pojo.entry.DeliveryAddress>
     **/
    public List<DeliveryAddress> getDeliveryAddressList() {
        // 获取当前操作用户
        Integer userId = ThreadLocalUtil.getUserId();
        // 获取当前操作用户地址列表
        return deliveryAddressService.lambdaQuery()
                .eq(DeliveryAddress::getUserId, userId)
                .list();
    }

    /**
     * 构造OrderPreVO.Summary结算信息
     *
     * @param totalPrice
     * @param totalPayPrice
     * @return com.wszqj.pojo.vo.OrderPreVO.Summary
     **/
    public OrderPreVO.Summary getSummary(double totalPrice, double totalPayPrice) {
        // 构造 OrderPreVO.Summary
        return OrderPreVO.Summary
                .builder()
                .totalPrice(BigDecimal.valueOf(totalPrice))
                .totalPayPrice(BigDecimal.valueOf(totalPayPrice).add(BigDecimal.valueOf(18)))
                .postFee(BigDecimal.valueOf(18))// TODO 暂定
                .build();
    }


    /**
     * 构造 OrderPreVO.OrderPreGoods 商品信息
     *
     * @param sku
     * @param count
     * @return com.wszqj.pojo.vo.OrderPreVO.OrderPreGoods
     **/
    public OrderPreVO.OrderPreGoods getOrderPreGoods(Sku sku, String count) {
        // 构造 OrderPreVO.OrderPreGoods 商品信息
        return OrderPreVO.OrderPreGoods
                .builder()
                .id(String.valueOf(sku.getId()))
                .name(sku.getSkuName())
                .image(sku.getPictures())
                .attrsText(JsonUtils.parseJsonToFormattedString(sku.getAttributes()))
                .skuId(String.valueOf(sku.getId()))
                .price(sku.getPrice()) // 优惠前
                .payPrice(sku.getPrice()) // 优惠后
                .count(Integer.parseInt(count))
                .build();
    }
}

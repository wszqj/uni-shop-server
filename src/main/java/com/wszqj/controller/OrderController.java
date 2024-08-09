package com.wszqj.controller;

import com.wszqj.mapper.OrderMapper;
import com.wszqj.pojo.dto.OrderDTO;
import com.wszqj.pojo.entry.Order;
import com.wszqj.pojo.query.OrderQuery;
import com.wszqj.pojo.result.PageResult;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.OrderDetailVO;
import com.wszqj.pojo.vo.OrderListVO;
import com.wszqj.pojo.vo.OrderPreVO;
import com.wszqj.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName OrderController
 * @description: 订单相关接口
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
@Tag(name = "订单相关接口")
public class OrderController {
    private final IOrderService orderService;
    private final OrderMapper orderMapper;


    /**
     * 获取预支付交易单
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    @Operation(summary = "获取预支付交易单")
    @GetMapping("/pre")
    public Result<OrderPreVO> getOrderPre() {
        log.info("获取预支付交易单");
        return orderService.getOrderPre();
    }

    /**
     * 立即购买，生成预支付订单
     *
     * @param skuId skuId
     * @param count 购买数量
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    @Operation(summary = "立即购买")
    @GetMapping("/buyNow")
    public Result<OrderPreVO> buyNow(
            @RequestParam("skuId") @Schema(description = "商品skuId") String skuId,
            @RequestParam("count") @Schema(description = "购买数量") String count) {
        log.info("立即购买，生成预支付订单 skuId={}, count={}", skuId, count);
        return orderService.buyNow(skuId, count);
    }

    /**
     * 创建订单
     *
     * @param orderDTO 创建订单参数
     * @return com.wszqj.pojo.result.Result<java.lang.String>
     **/
    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("创建订单：{}", orderDTO);
        return orderService.createOrder(orderDTO);
    }

    /**
     * 获取指定订单列表
     *
     * @param orderQuery 查询参数
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderListVO>
     **/
    @Operation(summary = "获取指定订单列表")
    @GetMapping("/list")
    public Result<PageResult<OrderListVO.OrderItem>> orderList(OrderQuery orderQuery) {
        log.info("获取指定订单列表：{}", orderQuery);
        return orderService.orderList(orderQuery);
    }

    /**
     * 获取订单详情
     *
     * @param id 订单id
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderDetailVO>
     **/
    @Operation(summary = "获取商品详情")
    @GetMapping("/detail")
    public Result<OrderDetailVO> getOrderDetail(@RequestParam("id") @Schema(description = "订单id") String id) {
        log.info("获取商品详情：{}", id);
        return orderService.getOrderDetail(id);
    }

    /**
     * 再次购买
     *
     * @param orderId 订单id
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    @Operation(summary = "再次购买")
    @GetMapping("/pre/again")
    public Result<OrderPreVO> getOrderPreAgain(@RequestParam("orderId") @Schema(description = "订单id") String orderId) {
        log.info("再次购买：{}", orderId);
        return orderService.getOrderPreAgain(orderId);
    }

    /**
     * 模拟订单支付
     *
     * @param id 订单id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "模拟订单支付")
    @PostMapping("/pay")
    public Result<Void> payOrder(@RequestParam("id") @Schema(description = "订单id") String id) {
        log.info("模拟订单支付：{}", id);
        return orderService.payOrder(id);
    }

    /**
     * 删除订单
     *
     * @param id 订单id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "删除订单")
    @DeleteMapping("/delete")
    public Result<Void> deleteOrder(@RequestParam("id") @Schema(description = "订单id") String id) {
        log.info("删除订单：{}", id);
        return orderService.deleteOrder(id);
    }

    /**
     * 取消订单
     *
     * @param id
     * @param reason
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "取消订单")
    @PutMapping("/cancel")
    public Result<Void> cancelOrder(@RequestParam("id") @Schema(description = "订单id") String id,
                                    @RequestParam("reason") @Schema(description = "取消原因") String reason) {
        log.info("取消订单：{},{}", id, reason);
        return orderService.cancelOrder(id, reason);
    }
}

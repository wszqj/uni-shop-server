package com.wszqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wszqj.pojo.dto.OrderDTO;
import com.wszqj.pojo.entry.Order;
import com.wszqj.pojo.query.OrderQuery;
import com.wszqj.pojo.result.PageResult;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.OrderDetailVO;
import com.wszqj.pojo.vo.OrderListVO;
import com.wszqj.pojo.vo.OrderPreVO;

import java.util.List;

/**
 * @ClassName IOrderService
 * @description: TODO
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
public interface IOrderService extends IService<Order> {
    /**
     * 获取预支付交易单
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    Result<OrderPreVO> getOrderPre();

    /**
     * 立即购买，生成预支付订单
     *
     * @param skuId
     * @param count
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    Result<OrderPreVO> buyNow(String skuId, String count);

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return com.wszqj.pojo.result.Result<java.lang.String>
     **/
    Result<String> createOrder(OrderDTO orderDTO);

    /**
     * 获取指定订单列表
     *
     * @param orderQuery
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.result.PageResult < com.wszqj.pojo.vo.OrderListVO.OrderItem>>
     **/
    Result<PageResult<OrderListVO.OrderItem>> orderList(OrderQuery orderQuery);

    /**
     * 获取商品详情
     *
     * @param id
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderDetailVO>
     **/
    Result<OrderDetailVO> getOrderDetail(String id);

    /**
     * 再次购买
     *
     * @param orderId
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.OrderPreVO>
     **/
    Result<OrderPreVO> getOrderPreAgain(String orderId);

    /**
     * 模拟订单支付
     *
     * @param id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> payOrder(String id);

    /**
     * 删除订单
     *
     * @param id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> deleteOrder(String id);

    /**
     * 取消订单
     *
     * @param id
     * @param reason
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> cancelOrder(String id, String reason);

}

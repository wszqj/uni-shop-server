package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName OrderDetails
 * @description: TODO
 * @date 2024年08月04日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("order_details")
public class OrderDetails {
    // 订单详情唯一标识符
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 关联的订单ID
    private String orderId;
    // 关联的商品ID
    private Integer spuId;
    // 下单时商品名
    private String skuName;
    // 所选地址ID
    private Integer addressId;
    // 商品SKU ID
    private Integer skuId;
    // 购买数量
    private Integer count;
    // 订单状态
    private Integer orderState;
    // 购买时单价
    private BigDecimal buyPrice;
    // 商品总价
    private BigDecimal totalPrice;
    // 应付总价
    private BigDecimal totalPayPrice;
    // 运费
    private BigDecimal postPrice;
    // 支付渠道
    private Integer payType;
    // 买家留言
    private String buyerMsg;
    // 订单取消原因
    private String cancelReason;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    // 支付时间
    private LocalDateTime payAt;
}
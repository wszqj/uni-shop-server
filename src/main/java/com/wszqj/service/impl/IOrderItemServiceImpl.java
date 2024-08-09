package com.wszqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.OrderItemMapper;
import com.wszqj.pojo.entry.OrderItem;
import com.wszqj.service.IOrderItemService;
import org.springframework.stereotype.Service;

/**
 * @ClassName IOrderItemServiceImpl
 * @description: TODO
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@Service
public class IOrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {
}

package com.wszqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.OrderDetailsMapper;
import com.wszqj.pojo.entry.OrderDetails;
import com.wszqj.service.IOrderDetailsService;
import org.springframework.stereotype.Service;

/**
 * @ClassName IOrderDetailsServiceImpl
 * @description: TODO
 * @date 2024年08月04日
 * @author: wszqj
 * @version: 1.0
 */
@Service
public class IOrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetails> implements IOrderDetailsService {
}

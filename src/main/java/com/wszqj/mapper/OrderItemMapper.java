package com.wszqj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wszqj.pojo.entry.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderItemMapper
 * @description: TODO
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}

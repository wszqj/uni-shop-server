package com.wszqj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wszqj.pojo.entry.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderMapper
 * @description: TODO
 * @date 2024年08月03日
 * @author: wszqj
 * @version: 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

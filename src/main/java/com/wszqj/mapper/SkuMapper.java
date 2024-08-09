package com.wszqj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wszqj.pojo.entry.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName SkuMapper
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    @Select("select id, goods_id, attributes, price, stock, pictures from wszqj_get_money.skus where id =#{id}")
    Sku selectByGoodsId(@Param("id") Integer id);
}

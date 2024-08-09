package com.wszqj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wszqj.pojo.entry.Goods;
import com.wszqj.pojo.entry.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName HomeMapper
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Mapper
public interface HomeMapper extends BaseMapper<Goods> {
    @Select("select id, goods_id, attributes, price, stock, created_at, updated_at, pictures from wszqj_get_money.skus where goods_id = #{goodsId}")
    List<Sku> selectSkusByGoodsId(@Param("goodsId") Long goodsId);
}

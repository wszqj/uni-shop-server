package com.wszqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wszqj.pojo.entry.Goods;
import com.wszqj.pojo.query.GoodsPageQuery;
import com.wszqj.pojo.result.PageResult;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.GoodsDetailVO;
import com.wszqj.pojo.vo.GoodsListVO;


/**
 * @ClassName HomeService
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
public interface IHomeService extends IService<Goods> {
    /**
     *  获取猜你喜欢商品列表
     * @param goodsPageQuery 查询参数
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.result.PageResult<com.wszqj.pojo.vo.GoodsListVO>>
     **/
    Result<PageResult<GoodsListVO>> pageQuery(GoodsPageQuery goodsPageQuery);

    /**
     *  根据Id获取商品详情
     * @param id 商品ID
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.GoodsDetailVO>
     **/
    Result<GoodsDetailVO> getGoodsDetailById(Long id);
}

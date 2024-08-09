package com.wszqj.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.CategoryMapper;
import com.wszqj.pojo.entry.Category;
import com.wszqj.pojo.entry.Goods;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.CategoryVO;
import com.wszqj.service.ICategoryService;
import com.wszqj.service.IGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @ClassName ICategoryServiceImpl
 * @description: TODO
 * @date 2024年07月26日
 * @author: wszqj
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class ICategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    private final IGoodsService goodsService;

    /**
     * 获取分类列表
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.CategoryVO>
     **/
    @Override
    public Result<List<CategoryVO>> getCategoryList() {
        // 获取所有的分类列表
        List<Category> categories = lambdaQuery().list();
        // 返回值列表
        List<CategoryVO> categoryVOList = new ArrayList<>();
        // 一级分类集合
        List<Category> fCategories = new ArrayList<>();
        // 二级分类集合
        List<Category> sCategories = new ArrayList<>();
        // 抽离父类以及子类
        categories.forEach(category -> {
            if (category.getParentId() == 0) {
                fCategories.add(category);
            } else {
                sCategories.add(category);
            }
        });

        // 遍历一级分类列表
        fCategories.forEach(fCategory -> {
            // 二级分类列表
            List<CategoryVO.SecondaryCategory> secondaryCategories = new ArrayList<>();
            CategoryVO categoryVO = CategoryVO.builder()
                    .id(fCategory.getId())
                    .name(fCategory.getName())
                    .picture(fCategory.getIcon())
                    .children(secondaryCategories)
                    .build();
            // 遍历二级分类列表
            sCategories.forEach(sCategory -> {
                if (Objects.equals(fCategory.getId(), sCategory.getParentId())) {
                    // 封装 SecondaryCategory 并存入二级分类列表
                    secondaryCategories.add(getSecondaryCategory(sCategory));
                }
            });
            categoryVOList.add(categoryVO);
        });


        // 查询所有商品数据
        List<Goods> goodsList = goodsService.lambdaQuery().list();
        // 遍历一级分类集合为其赋值数据
        categoryVOList.forEach(categoryVO -> {
            // 遍历 二级分类类列表
            categoryVO.getChildren().forEach(category -> {
                // 遍历 商品列表
                goodsList.forEach(goods -> {
                    if (Objects.equals(category.getId(), goods.getCategoryId())) {
                        category.getGoods().add(getGoods(goods));
                    }
                });
            });
        });
        return Result.success(categoryVOList);
    }

    /**
     * 封装 SecondaryCategory
     *
     * @param category
     * @return com.wszqj.pojo.vo.CategoryVO.SecondaryCategory
     **/
    public static CategoryVO.SecondaryCategory getSecondaryCategory(Category category) {
        // 二级分类商品列表
        List<CategoryVO.SecondaryCategory.Goods> secondaryCategoryGoods = new ArrayList<>();
        return com.wszqj.pojo.vo.CategoryVO.SecondaryCategory
                .builder()
                .id(category.getId())
                .name(category.getName())
                .picture(category.getIcon())
                .goods(secondaryCategoryGoods)
                .build();
    }

    /**
     *  封装 CategoryVO.SecondaryCategory.Goods
     * @param goods
     * @return com.wszqj.pojo.vo.CategoryVO.SecondaryCategory.Goods
     **/
    public static CategoryVO.SecondaryCategory.Goods getGoods(Goods goods){
        return com.wszqj.pojo.vo.CategoryVO.SecondaryCategory.Goods.builder()
                .id(goods.getId())
                .name(goods.getName())
                .desc(goods.getDescription())
                .price(String.valueOf(goods.getPrice()))
                .picture(JSONArray.parseArray(goods.getMainVideos()).getFirst().toString())
                .build();
    }
}

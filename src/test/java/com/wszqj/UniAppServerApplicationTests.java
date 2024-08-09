package com.wszqj;

import com.wszqj.pojo.entry.Category;
import com.wszqj.pojo.entry.Goods;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.CategoryVO;
import com.wszqj.service.ICategoryService;
import com.wszqj.service.IGoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static com.wszqj.service.impl.ICategoryServiceImpl.getGoods;
import static com.wszqj.service.impl.ICategoryServiceImpl.getSecondaryCategory;

@SpringBootTest
class UniAppServerApplicationTests {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IGoodsService goodsService;

    /**
     * 获取分类列表
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.CategoryVO>
     **/
    @Test
    public void getCategoryList() {
        // 获取所有的分类列表
        List<Category> categories = categoryService.lambdaQuery().list();
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
        System.out.println("categoryVOList = " + categoryVOList);
    }

}

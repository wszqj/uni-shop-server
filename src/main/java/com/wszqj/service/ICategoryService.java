package com.wszqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wszqj.pojo.entry.Category;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.CategoryVO;

import java.util.List;

/**
 * @ClassName ICategoryService
 * @description: TODO
 * @date 2024年07月26日
 * @author: wszqj
 * @version: 1.0
 */
public interface ICategoryService extends IService<Category> {
    /**
     *  获取分类列表
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.CategoryVO>
     **/
    Result<List<CategoryVO>> getCategoryList();
}

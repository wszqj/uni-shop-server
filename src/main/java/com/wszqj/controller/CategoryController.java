package com.wszqj.controller;

import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.CategoryVO;
import com.wszqj.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CategoryController
 * @description: 分类相关接口
 * @date 2024年08月07日
 * @author: wszqj
 * @version: 1.0
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
@Tag(name = "分类相关接口")
public class CategoryController {
    private final ICategoryService categoryService;

    /**
     *  获取分类列表
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.CategoryVO>
     **/
    @Operation(summary = "获取分类列表")
    @GetMapping("/list")
    public Result<List<CategoryVO>> getCategoryList(){
        log.info("获取分类列表");
        return categoryService.getCategoryList();
    }
}

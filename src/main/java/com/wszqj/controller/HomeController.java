package com.wszqj.controller;

import com.wszqj.pojo.entry.Advertisement;
import com.wszqj.pojo.query.GoodsPageQuery;
import com.wszqj.pojo.result.PageResult;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.GoodsDetailVO;
import com.wszqj.pojo.vo.GoodsListVO;
import com.wszqj.service.IAdvertisementService;
import com.wszqj.service.IHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HomeController
 * @description: 首页相关接口
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@RestController
@RequestMapping("/home")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "主页相关接口")
public class HomeController {

    private final IHomeService homeService;
    private final IAdvertisementService advertisementService;

    /**
     * 获取猜你喜欢商品列表
     *
     * @param page 页码
     * @param pageSize 数据页大小
     * @param name 商品名称
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.result.PageResult < com.wszqj.pojo.vo.GoodsListVO>>
     **/
    @Operation(summary = "获取猜你喜欢商品列表")
    @GetMapping("/list")
    public Result<PageResult<GoodsListVO>> getGoodsList(
            @RequestParam("page") @Schema(description = "页码") Integer page,
            @RequestParam("pageSize") @Schema(description = "页大小") Integer pageSize,
            @RequestParam("name") @Schema(description = "商品名称") String name
    ) {
        // 封装查询参数
        GoodsPageQuery goodsPageQuery = GoodsPageQuery
                .builder()
                .page(page)
                .pageSize(pageSize)
                .name(name)
                .build();
        log.info("商品分页搜索：{}", goodsPageQuery);
        return homeService.pageQuery(goodsPageQuery);
    }

    /**
     * 根据Id获取商品详情
     *
     * @param id 商品ID
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.GoodsDetailVO>
     **/
    @Operation(summary = "根据Id获取商品详情")
    @GetMapping("/detail")
    public Result<GoodsDetailVO> getGoodsDetailById(@RequestParam("id") @Schema(description = "商品ID") Long id) {
        log.info("根据商品ID查询商品的详情：{}", id);
        return homeService.getGoodsDetailById(id);
    }

    /**
     * 获取广告轮播图
     *
     * @return com.wszqj.pojo.result.Result<java.util.List < com.wszqj.pojo.entry.Advertisement>>
     **/
    @Operation(summary = "获取广告轮播图")
    @GetMapping("/advertisement")
    public Result<List<Advertisement>> getAdvertisementList() {
        List<Advertisement> list = advertisementService.list();
        return Result.success(list);
    }
}

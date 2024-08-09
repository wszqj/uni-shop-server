package com.wszqj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.BrandMapper;
import com.wszqj.mapper.CategoryMapper;
import com.wszqj.mapper.DiscountMapper;
import com.wszqj.mapper.HomeMapper;
import com.wszqj.pojo.entry.*;
import com.wszqj.pojo.query.GoodsPageQuery;
import com.wszqj.pojo.result.PageResult;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.GoodsDetailVO;
import com.wszqj.pojo.vo.GoodsListVO;
import com.wszqj.pojo.vo.SkuVO;
import com.wszqj.service.IHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName IHomeServiceImpl
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class IHomeServiceImpl extends ServiceImpl<HomeMapper, Goods> implements IHomeService {
    private final HomeMapper homeMapper;
    private final DiscountMapper discountMapper;
    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;

    /**
     *  获取猜你喜欢商品列表
     * @param goodsPageQuery 查询参数
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.result.PageResult<com.wszqj.pojo.vo.GoodsListVO>>
     **/
    @Override
    public Result<PageResult<GoodsListVO>> pageQuery(GoodsPageQuery goodsPageQuery) {
        // 创建分页条件，指定当前页和每页条数
        Page<Goods> p = Page.of(goodsPageQuery.getPage(), goodsPageQuery.getPageSize());
        // 指定分页的排毒规则
        // 查询条件
        Page<Goods> page = lambdaQuery()
                .like(goodsPageQuery.getName() != null, Goods::getName, goodsPageQuery.getName())
                .page(p);
        // 数据转换
        List<Goods> goodsList = page.getRecords();
        // 存储转换完毕的数据
        List<GoodsListVO> goodsListVOS = new ArrayList<>(goodsList.size());
        for (Goods goods : goodsList) {
            // 反序列化为JSON数据
            JSONArray jsonArray = JSONArray.parseArray(goods.getMainVideos());
            // 获取第一个元素
            String picture = (String) jsonArray.getFirst();
            // 数据拷贝
            goodsListVOS.add(GoodsListVO.builder()
                    .id(goods.getId())
                    .price(goods.getPrice())
                    .name(goods.getName())
                    .picture(picture)
                    .build());
        }
        // 创建最终分页数据
        Page<GoodsListVO> resultPage = new Page<>();
        // 数据
        resultPage.setRecords(goodsListVOS);
        // 总条数
        resultPage.setTotal(page.getTotal());
        // 每页条数
        resultPage.setSize(page.getSize());
        // 当前页数
        resultPage.setCurrent(page.getCurrent());
        // 总页数
        resultPage.setPages(page.getPages());
        return Result.success(new PageResult<>
                (resultPage.getTotal(),
                        resultPage.getSize(),
                        resultPage.getPages(),
                        resultPage.getCurrent(),
                        resultPage.getRecords()));
    }

    /**
     *  根据Id获取商品详情
     * @param id 商品ID
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.GoodsDetailVO>
     **/
    @Override
    public Result<GoodsDetailVO> getGoodsDetailById(Long id) {
        // 根据商品的id获取其对应的sku列表
        List<Sku> skus = homeMapper.selectSkusByGoodsId(id);

        // 用于存储属性及其出现的顺序
        Map<String, Integer> attributeOrderMap = new LinkedHashMap<>();
        // 用于存储不同键及其对应的值的集合
        Map<String, Set<String>> attributeMap = new HashMap<>();

        // skuVO列表
        List<SkuVO> skuVOS = skus.stream().map(sku -> {
            SkuVO skuVO = new SkuVO();
            List<String> attributes = parseAttributes(sku.getAttributes(), attributeMap, attributeOrderMap);
            BeanUtil.copyProperties(sku, skuVO);
            skuVO.setAttributes(attributes);
            return skuVO;
        }).collect(Collectors.toList());

        // 从 attributeMap 的 entrySet 开始创建一个流，并根据 attributeOrderMap 中的顺序排序
        List<GoodsDetailVO.Spec> specs = attributeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(attributeOrderMap::get)))
                .map(entry -> new GoodsDetailVO.Spec(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(GoodsDetailVO.Attribute::new)
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());

        // 获取商品
        Goods goods = homeMapper.selectById(id);
        if (goods == null){
            return Result.error("不存在该商品");
        }
        // 图片集合
        List<String> images = new ArrayList<>();
        //解析图片集合
        JSONArray.parseArray(goods.getMainVideos()).forEach(image -> images.add(String.valueOf(image)));

        // 属性详情
        List<GoodsDetailVO.Detail> details=new ArrayList<>();
        // 解析详情
        JSONObject.parse(goods.getDetails()).forEach((key, value) -> details.add(GoodsDetailVO.Detail.builder()
                .name(String.valueOf(key))
                .value(String.valueOf(value))
                .build()));

        // 构建返回数据
        return Result.success(GoodsDetailVO.builder()
                .id(goods.getId())
                .name(goods.getName())
                .description(goods.getDescription())
                .mainVideos(images)
                .videoScale(goods.getVideoScale())
                .price(goods.getPrice())
                .oldPrice(goods.getOldPrice())
                .details(details)
                .createdAt(goods.getCreatedAt())
                .status(goods.getStatus())
                .category(categoryMapper.selectById(goods.getCategoryId()))
                .brand(brandMapper.selectById(goods.getBrandId()))
                .discount(discountMapper.selectById(goods.getDiscountId()))
                .skus(skuVOS)
                .specs(specs)
                .build());
    }

    /**
     * 解析 SKU 的属性 JSON 并更新属性映射表及其顺序
     *
     * @param attributesJson    SKU 属性 JSON 字符串
     * @param attributeMap      属性映射表
     * @param attributeOrderMap 属性顺序映射表
     * @return 属性值列表
     */
    private List<String> parseAttributes(String attributesJson, Map<String, Set<String>> attributeMap, Map<String, Integer> attributeOrderMap) {
        List<String> attributes = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(attributesJson);
        int order = 0;
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            attributes.add(value);
            attributeMap.putIfAbsent(key, new HashSet<>());
            attributeMap.get(key).add(value);

            // 更新属性顺序，如果该属性名不存在于 attributeOrderMap 中，则添加
            attributeOrderMap.putIfAbsent(key, order++);
        }
        return attributes;
    }

}

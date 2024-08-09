package com.wszqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.GoodsMapper;
import com.wszqj.pojo.entry.Goods;
import com.wszqj.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * @ClassName IGoodsServiceImpl
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Service
public class IGoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
}

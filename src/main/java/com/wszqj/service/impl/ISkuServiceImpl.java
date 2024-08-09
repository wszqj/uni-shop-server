package com.wszqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.SkuMapper;
import com.wszqj.pojo.entry.Sku;
import com.wszqj.service.ISkuService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ISkuServiceImpl
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
@Service
public class ISkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {
}

package com.wszqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.BrandMapper;
import com.wszqj.pojo.entry.Brand;
import com.wszqj.service.IBrandService;
import org.springframework.stereotype.Service;

/**
 * @ClassName BrandServiceImpl
 * @description: TODO
 * @date 2024年07月26日
 * @author: wszqj
 * @version: 1.0
 */
@Service
public class IBrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
}

package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName Brand
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("brands")
public class Brand {

    // 主键，自增
    @TableId(type = IdType.AUTO)//设置主键的自增策略为主键自增（mybatisPlus默认为雪花算法）
    private Integer id;

    // 品牌名称，不能为空
    private String name;

    // 品牌描述，可为空
    private String description;

    // 创建时间，默认当前时间
    private LocalDateTime createdAt;

    // 更新时间，默认当前时间，更新时自动设置
    private LocalDateTime updatedAt;
}

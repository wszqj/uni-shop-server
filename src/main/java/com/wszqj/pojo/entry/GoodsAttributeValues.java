package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName GoodsAttributeValues
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAttributeValues {
    // 主键，自增
    @TableId(type = IdType.AUTO)//设置主键的自增策略为主键自增（mybatisPlus默认为雪花算法）
    private Integer id;

    // 外键，指向category_attribute表，不能为空
    private Integer categoryAttributeId;

    // 外键，指向产品表，不能为空
    private Integer productId;

    // 属性值，不能为空
    private String value;

    // 创建时间，默认当前时间
    private LocalDateTime createdAt;

    // 更新时间，默认当前时间
    private LocalDateTime updatedAt;
}

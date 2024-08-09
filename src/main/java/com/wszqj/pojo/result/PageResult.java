package com.wszqj.pojo.result;

/**
 * @ClassName PageResult
 * @description: TODO
 * @date 2024年03月20日
 * @author: wszqj
 * @version: 1.0
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> implements Serializable {
    // 总记录数
    private long total;
    // 每页条数
    private long pageSize;
    // 总页数
    private long pages;
    // 当前页数
    private long page;
    // 当前页数据集合
    private List<T> records;
}

package com.wszqj.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.NumberUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName CollUtils
 * @description: 继承自 hutool 的集合工具类 提供了一些常用的集合操作方法
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
public class CollUtils extends CollectionUtil {

    /**
     * 返回一个空的不可变列表
     *
     * @param <T> 列表元素的类型
     * @return 空的不可变列表
     */
    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    /**
     * 返回一个空的不可变集合
     *
     * @param <T> 集合元素的类型
     * @return 空的不可变集合
     */
    public static <T> Set<T> emptySet() {
        return Collections.emptySet();
    }

    /**
     * 返回一个空的不可变映射
     *
     * @param <K> 映射键的类型
     * @param <V> 映射值的类型
     * @return 空的不可变映射
     */
    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    /**
     * 返回一个仅包含单个元素的不可变集合
     *
     * @param <T> 集合元素的类型
     * @param t   集合中的唯一元素
     * @return 包含单个元素的不可变集合
     */
    public static <T> Set<T> singletonSet(T t) {
        return Collections.singleton(t);
    }

    /**
     * 返回一个仅包含单个元素的不可变列表
     *
     * @param <T> 列表元素的类型
     * @param t   列表中的唯一元素
     * @return 包含单个元素的不可变列表
     */
    public static <T> List<T> singletonList(T t) {
        return Collections.singletonList(t);
    }

    /**
     * 将字符串列表转换为整数列表
     *
     * @param originList 原始字符串列表
     * @return 转换后的整数列表，如果原始列表为空或null，则返回null
     */
    public static List<Integer> convertToInteger(List<String> originList) {
        return CollUtils.isNotEmpty(originList) ? originList.stream().map(NumberUtil::parseInt).collect(Collectors.toList()) : null;
    }

    /**
     * 将字符串列表转换为长整数列表
     *
     * @param originList 原始字符串列表
     * @return 转换后的长整数列表，如果原始列表为空或null，则返回null
     */
    public static List<Long> convertToLong(List<String> originList) {
        return CollUtils.isNotEmpty(originList) ? originList.stream().map(NumberUtil::parseLong).collect(Collectors.toList()) : null;
    }

    /**
     * 以指定的分隔符将集合转换为字符串
     * 如果集合元素为数组、Iterable或Iterator，则递归组合其为字符串
     *
     * @param collection  集合
     * @param conjunction 分隔符
     * @param <T>         集合元素类型
     * @return 连接后的字符串，如果集合为空或null，则返回null
     * @see IterUtil#join(Iterator, CharSequence)
     */
    public static <T> String join(Collection<T> collection, CharSequence conjunction) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return IterUtil.join(collection.iterator(), conjunction);
    }

    /**
     * 以指定的分隔符将非null集合元素转换为字符串
     *
     * @param collection  集合
     * @param conjunction 分隔符
     * @param <T>         集合元素类型
     * @return 连接后的字符串，如果集合为空或null，则返回null
     */
    public static <T> String joinIgnoreNull(Collection<T> collection, CharSequence conjunction) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (T t : collection) {
            if (t == null) continue;
            sb.append(t).append(conjunction);
        }
        if (sb.length() <= 0) {
            return null;
        }
        // 删除最后一个分隔符
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}

package com.wszqj.utils;
import java.util.Map;

import static com.wszqj.constant.BaseConstant.ID;
import static com.wszqj.constant.BaseConstant.NICKNAME;

/**
 * @ClassName ThreadLocalUtil
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    //提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    //存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }


    //清除ThreadLocal 防止内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    // 获取当前操作用户ID
    public static Integer getUserId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        return (Integer) map.get(ID);
    }

    // 获取当前操作用户昵称
    public static String getUserNickName() {
        Map<String, Object> map = ThreadLocalUtil.get();
        return (String) map.get(NICKNAME);
    }
}


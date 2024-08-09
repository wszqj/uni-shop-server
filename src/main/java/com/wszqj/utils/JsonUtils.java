package com.wszqj.utils;

import com.alibaba.fastjson2.JSONObject;

/**
 * @ClassName JsonUtils
 * @description: TODO
 * @date 2024年07月31日
 * @author: wszqj
 * @version: 1.0
 */
public class JsonUtils {
    /**
     * 解析 JSON 字符串并将其转换为格式化字符串
     *
     * @param jsonString JSON 字符串
     * @return 格式化后的字符串
     */
    public static String parseJsonToFormattedString(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        StringBuilder formattedString = new StringBuilder();
        for (String key : jsonObject.keySet()) {
            String value = jsonObject.getString(key);
            if (!formattedString.isEmpty()) {
                formattedString.append(" ");
            }
            formattedString.append(key).append("：").append(value);
        }
        return formattedString.toString();
    }
}

package com.wszqj.pojo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Result
 * @description: 通用的API响应封装类
 * @date 2024年03月20日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Result",description = "通用返回对象")
public class Result<T> {
    /**
     * 业务状态码: "0" - 成功, "1" - 失败
     */
    @Schema(name = "code",description = "状态码")
    private String code;

    /**
     * 响应提示信息
     */
    @Schema(name = "msg",description = "响应提示信息")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(name = "result",description = "响应数据")
    private T result;

    /**
     * 快速返回带数据的成功响应结果
     *
     * @param result 响应数据
     * @param <E> 数据类型
     * @return 带数据的成功Result实例
     */
    public static <E> Result<E> success(E result) {
        return new Result<>("0", "查询成功", result);
    }

    /**
     * 快速返回带数据的成功响应结果
     *
     * @param <E> 数据类型
     * @return 带数据的成功Result实例
     */
    public static <E> Result<E> success() {
        return new Result<>("0", "操作成功", null);
    }

    /**
     * 快速返回带数据和自定义信息的成功响应结果
     *
     * @param result 响应数据
     * @param msg 自定义提示信息
     * @param <E> 数据类型
     * @return 带数据和自定义信息的成功Result实例
     */
    public static <E> Result<E> success(E result, String msg) {
        return new Result<>("0", msg, result);
    }

    /**
     * 快速返回仅有自定义信息的成功响应结果
     *
     * @param msg 自定义提示信息
     * @return 仅有自定义信息的成功Result实例
     */
    public static <E> Result<E> success(String msg) {
        return new Result<>("0", msg, null);
    }

    /**
     * 快速返回带自定义信息的错误响应结果
     *
     * @param msg 自定义提示信息
     * @return 带自定义信息的错误Result实例
     */
    public static <E> Result<E> error(String msg) {
        return new Result<>("1", msg, null);
    }
}

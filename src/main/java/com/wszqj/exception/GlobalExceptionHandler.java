package com.wszqj.exception;

import com.wszqj.pojo.result.Result;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @description 全局异常处理器，用于统一处理应用程序中的异常
 * @date 2024年07月24日
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理验证异常和数据库未分类SQL异常
     *
     * @param e 异常对象，可以是MethodArgumentNotValidException或UncategorizedSQLException
     * @return 返回一个包含错误信息的Result对象
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, UncategorizedSQLException.class})
    public Result<Void> handleValidationExceptions(Exception e) {
        // 打印异常堆栈信息
        e.printStackTrace();
        // 返回统一的错误信息
        return Result.error("必要信息格式有误");
    }

    /**
     * 处理所有其他未捕获的异常
     *
     * @param e 异常对象
     * @return 返回一个包含错误信息的Result对象
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 打印异常堆栈信息
        e.printStackTrace();
        // 获取异常信息，如果异常信息为空，则返回"服务异常"
        String errorMessage = StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "服务异常";
        // 返回包含错误信息的Result对象
        return Result.error(errorMessage);
    }
}

package com.wszqj.exception;

/**
 * @ClassName OrderException
 * @description: TODO
 * @date 2024年08月05日
 * @author: wszqj
 * @version: 1.0
 */
public class OrderException extends BaseException{
    public OrderException(String message) {
        super(message, 1);
    }

    public OrderException(String message, Throwable cause, int code) {
        super(message, cause, 1);
    }

    public OrderException(Throwable cause, int code) {
        super(cause, 1);
    }
}

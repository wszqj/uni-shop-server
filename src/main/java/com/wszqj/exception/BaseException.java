package com.wszqj.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private int code;

    public BaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}

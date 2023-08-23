package com.techit.withus.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final String detail;
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, Object...args) {
        this.detail = String.format(errorCode.getMessage(), args);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        this.detail = errorCode.getMessage();
        this.errorCode = errorCode;
    }
}

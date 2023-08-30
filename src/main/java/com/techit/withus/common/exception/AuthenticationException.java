package com.techit.withus.common.exception;

public class AuthenticationException extends BusinessException{

    public AuthenticationException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

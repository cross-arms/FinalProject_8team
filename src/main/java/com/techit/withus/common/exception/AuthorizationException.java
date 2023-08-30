package com.techit.withus.common.exception;

public class AuthorizationException extends BusinessException{

    public AuthorizationException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

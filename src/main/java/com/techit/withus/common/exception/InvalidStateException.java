package com.techit.withus.common.exception;

public class InvalidStateException extends BusinessException{

    public InvalidStateException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public InvalidStateException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.techit.withus.common.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.techit.withus.common.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EntityNotFoundException(ErrorCode errorCode, Long userId) {
        super(errorCode, String.format(errorCode.getMessage(), userId));
    }
}

package com.techit.withus.common.exception;

import lombok.Getter;


public class InvalidValueException extends BusinessException{

    public InvalidValueException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}

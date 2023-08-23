package com.techit.withus.common.exception;

public class FileProcessingException extends BusinessException{
    public FileProcessingException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public FileProcessingException(ErrorCode errorCode) {
        super(errorCode);
    }
}

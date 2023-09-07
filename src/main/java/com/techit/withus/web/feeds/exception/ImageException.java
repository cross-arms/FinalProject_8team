package com.techit.withus.web.feeds.exception;

import com.techit.withus.common.exception.BusinessException;
import com.techit.withus.common.exception.ErrorCode;

import static com.techit.withus.common.exception.ErrorCode.DIRECTORY_CREATION_FAILED;

public class ImageException extends BusinessException {

    public ImageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ImageException() {
        super(DIRECTORY_CREATION_FAILED);
    }
}

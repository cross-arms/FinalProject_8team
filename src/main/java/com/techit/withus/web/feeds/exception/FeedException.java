package com.techit.withus.web.feeds.exception;

import com.techit.withus.common.exception.BusinessException;
import com.techit.withus.common.exception.ErrorCode;

public class FeedException extends BusinessException {

    public FeedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

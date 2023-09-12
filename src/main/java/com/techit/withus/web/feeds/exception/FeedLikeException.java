package com.techit.withus.web.feeds.exception;

import com.techit.withus.common.exception.BusinessException;
import com.techit.withus.common.exception.ErrorCode;

public class FeedLikeException extends BusinessException {

    public FeedLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}

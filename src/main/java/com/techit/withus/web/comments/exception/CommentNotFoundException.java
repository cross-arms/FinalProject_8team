package com.techit.withus.web.comments.exception;

import com.techit.withus.common.exception.BusinessException;

import static com.techit.withus.common.exception.ErrorCode.COMMENT_NOT_FOUND;

public class CommentNotFoundException extends BusinessException {

    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND);
    }
}

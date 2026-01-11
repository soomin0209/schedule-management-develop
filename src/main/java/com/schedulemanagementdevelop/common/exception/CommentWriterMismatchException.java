package com.schedulemanagementdevelop.common.exception;

import org.springframework.http.HttpStatus;

public class CommentWriterMismatchException extends ServiceException {
    public CommentWriterMismatchException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

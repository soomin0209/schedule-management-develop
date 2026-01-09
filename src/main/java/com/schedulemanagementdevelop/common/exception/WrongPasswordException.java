package com.schedulemanagementdevelop.common.exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends ServiceException {
    public WrongPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

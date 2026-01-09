package com.schedulemanagementdevelop;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends ServiceException {
    public WrongPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

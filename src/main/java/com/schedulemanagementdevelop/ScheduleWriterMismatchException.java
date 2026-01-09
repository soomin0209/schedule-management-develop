package com.schedulemanagementdevelop;

import org.springframework.http.HttpStatus;

public class ScheduleWriterMismatchException extends ServiceException {
    public ScheduleWriterMismatchException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

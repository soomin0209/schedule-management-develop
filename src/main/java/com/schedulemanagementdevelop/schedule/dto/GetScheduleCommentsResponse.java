package com.schedulemanagementdevelop.schedule.dto;

import java.time.LocalDateTime;

public class GetScheduleCommentsResponse {

    private final String content;
    private final Long userId;
    private final LocalDateTime createdAt;

    public GetScheduleCommentsResponse(String content, Long userId, LocalDateTime createdAt) {
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}

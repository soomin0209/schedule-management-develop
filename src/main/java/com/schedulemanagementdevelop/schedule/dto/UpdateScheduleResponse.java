package com.schedulemanagementdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {

    private final Long id;
    private final String writer;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Long id, String writer, String title, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

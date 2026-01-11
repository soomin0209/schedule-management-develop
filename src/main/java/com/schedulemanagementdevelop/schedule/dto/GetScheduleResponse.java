package com.schedulemanagementdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleResponse {

    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetScheduleCommentsResponse> comments;

    public GetScheduleResponse(Long id, Long userId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, List<GetScheduleCommentsResponse> comments) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}

package com.schedulemanagementdevelop.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserSchedulesResponse {

    private final String title;
    private final LocalDateTime modifiedAt;

    public GetUserSchedulesResponse(String title, LocalDateTime modifiedAt) {
        this.title = title;
        this.modifiedAt = modifiedAt;
    }
}

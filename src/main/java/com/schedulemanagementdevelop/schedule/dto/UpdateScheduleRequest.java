package com.schedulemanagementdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "제목은 30자 이내로 입력해주세요.")
    private String title;
}

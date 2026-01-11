package com.schedulemanagementdevelop.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 5, message = "이름은 5자 이내로 입력해주세요.")
    private String name;
}

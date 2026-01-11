package com.schedulemanagementdevelop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupUserRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 5, message = "이름은 5자 이내로 입력해주세요.")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}

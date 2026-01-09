package com.schedulemanagementdevelop.user.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String name;
    private String email;
    private String password;
}

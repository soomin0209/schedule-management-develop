package com.schedulemanagementdevelop.user.controller;

import com.schedulemanagementdevelop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
}

package com.schedulemanagementdevelop.user.controller;

import com.schedulemanagementdevelop.user.dto.CreateUserRequest;
import com.schedulemanagementdevelop.user.dto.CreateUserResponse;
import com.schedulemanagementdevelop.user.dto.GetUserResponse;
import com.schedulemanagementdevelop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
}

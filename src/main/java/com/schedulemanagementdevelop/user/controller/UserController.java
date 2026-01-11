package com.schedulemanagementdevelop.user.controller;

import com.schedulemanagementdevelop.user.dto.*;
import com.schedulemanagementdevelop.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성 (회원가입)
    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponse> signup(
            @Valid @RequestBody SignupUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginUserRequest request,
            HttpSession session
    ) {
        session.setAttribute("loginUser", userService.login(request));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<GetUsersResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    // 유저 단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    // 유저 수정 (회원정보수정)
    @PatchMapping("/users")
    public ResponseEntity<UpdateUserResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser.getId(), request));
    }

    // 유저 삭제 (회원탈퇴)
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.delete(sessionUser.getId());
        session.invalidate();   // 유저 삭제 시 세션 무효화
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

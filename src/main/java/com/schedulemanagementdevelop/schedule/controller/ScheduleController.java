package com.schedulemanagementdevelop.schedule.controller;

import com.schedulemanagementdevelop.common.exception.UnauthorizedException;
import com.schedulemanagementdevelop.schedule.dto.*;
import com.schedulemanagementdevelop.schedule.service.ScheduleService;
import com.schedulemanagementdevelop.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser.getId(), request));
    }

    // 일정 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetSchedulesResponse>> getAll(
            @RequestParam(required = false) Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(userId));
    }

    // 일정 단건 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOne(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    // 일정 수정
    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(sessionUser.getId(), scheduleId, request));
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId
    ) {
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        scheduleService.delete(sessionUser.getId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

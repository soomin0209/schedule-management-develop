package com.schedulemanagementdevelop.comment.controller;

import com.schedulemanagementdevelop.comment.dto.*;
import com.schedulemanagementdevelop.comment.service.CommentService;
import com.schedulemanagementdevelop.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(sessionUser.getId(), scheduleId, request));
    }

    // 댓글 조회
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getAll(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId));
    }

    // 댓글 수정
    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> update(
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(sessionUser.getId(),scheduleId, commentId, request));
    }

    // 댓글 삭제
    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        commentService.delete(sessionUser.getId(), scheduleId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

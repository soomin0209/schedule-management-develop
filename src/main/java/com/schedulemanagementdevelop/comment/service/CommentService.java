package com.schedulemanagementdevelop.comment.service;

import com.schedulemanagementdevelop.comment.dto.*;
import com.schedulemanagementdevelop.comment.entity.Comment;
import com.schedulemanagementdevelop.comment.repository.CommentRepository;
import com.schedulemanagementdevelop.common.exception.*;
import com.schedulemanagementdevelop.schedule.entity.Schedule;
import com.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import com.schedulemanagementdevelop.user.entity.User;
import com.schedulemanagementdevelop.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public CreateCommentResponse save(Long userId, Long scheduleId, @Valid CreateCommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        Comment comment = new Comment(request.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> findAll(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        List<Comment> comments = commentRepository.findByScheduleIdOrderByCreatedAt(scheduleId);
        return comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).toList();
    }

    // 댓글 수정
    @Transactional
    public UpdateCommentResponse update(Long userId, Long scheduleId, Long commentId, @Valid UpdateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("없는 댓글입니다.")
        );
        if (!comment.getUser().getId().equals(userId)) {
            throw new CommentWriterMismatchException("해당 댓글의 작성자가 아닙니다.");
        }
        comment.update(request.getContent());
        return new UpdateCommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long userId, Long scheduleId, Long commentId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("없는 댓글입니다.")
        );
        if (!comment.getUser().getId().equals(userId)) {
            throw new CommentWriterMismatchException("해당 댓글의 작성자가 아닙니다.");
        }
        commentRepository.deleteById(commentId);
    }
}

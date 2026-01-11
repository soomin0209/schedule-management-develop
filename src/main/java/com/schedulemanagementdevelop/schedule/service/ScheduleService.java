package com.schedulemanagementdevelop.schedule.service;

import com.schedulemanagementdevelop.comment.entity.Comment;
import com.schedulemanagementdevelop.comment.repository.CommentRepository;
import com.schedulemanagementdevelop.common.exception.ScheduleNotFoundException;
import com.schedulemanagementdevelop.common.exception.ScheduleWriterMismatchException;
import com.schedulemanagementdevelop.common.exception.UserNotFoundException;
import com.schedulemanagementdevelop.schedule.dto.*;
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
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateScheduleResponse save(Long userId, @Valid CreateScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        Schedule schedule = new Schedule(user, request.getTitle(), request.getContent());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> findAll(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserIdOrderByModifiedAtDesc(userId);
        return schedules.stream()
                .map(schedule -> new GetSchedulesResponse(
                        schedule.getId(),
                        schedule.getUser().getId(),
                        schedule.getTitle(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        List<Comment> comments = commentRepository.findByScheduleIdOrderByCreatedAt(scheduleId);
        List<GetScheduleCommentsResponse> scheduleComments = comments.stream()
                .map(comment -> new GetScheduleCommentsResponse(
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getCreatedAt()
                )).toList();
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                scheduleComments
        );
    }

    @Transactional
    public UpdateScheduleResponse update(Long userId, Long scheduleId, @Valid UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        if (!schedule.getUser().getId().equals(userId)) {
            throw new ScheduleWriterMismatchException("해당 일정의 작성자가 아닙니다.");
        }

        schedule.update(request.getTitle());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        if (!schedule.getUser().getId().equals(userId)) {
            throw new ScheduleWriterMismatchException("해당 일정의 작성자가 아닙니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}

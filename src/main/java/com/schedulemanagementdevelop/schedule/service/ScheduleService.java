package com.schedulemanagementdevelop.schedule.service;

import com.schedulemanagementdevelop.schedule.dto.*;
import com.schedulemanagementdevelop.schedule.entity.Schedule;
import com.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.getWriter(), request.getTitle(), request.getContent());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getWriter(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> findAll(String writer) {
        List<Schedule> schedules = scheduleRepository.findByWriter(writer);
        return schedules.stream()
                .map(schedule -> new GetSchedulesResponse(
                        schedule.getId(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getWriter(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        schedule.update(request.getWriter(), request.getTitle());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getWriter(),
                schedule.getTitle(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}

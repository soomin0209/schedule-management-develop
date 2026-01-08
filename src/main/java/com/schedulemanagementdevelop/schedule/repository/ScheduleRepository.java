package com.schedulemanagementdevelop.schedule.repository;

import com.schedulemanagementdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

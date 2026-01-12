package com.schedulemanagementdevelop.schedule.repository;

import com.schedulemanagementdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE :userId IS NULL OR s.user.id = :userId")
    List<Schedule> findByUserIdOrderByModifiedAtDesc(Long userId);
}

package com.schedulemanagementdevelop.schedule.repository;

import com.schedulemanagementdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE :writer IS NULL OR s.writer = :writer")
    List<Schedule> findByWriter(@Param("writer") String writer);
}

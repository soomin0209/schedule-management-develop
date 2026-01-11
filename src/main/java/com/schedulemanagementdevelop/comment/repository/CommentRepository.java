package com.schedulemanagementdevelop.comment.repository;

import com.schedulemanagementdevelop.comment.entity.Comment;
import com.schedulemanagementdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleId(Long scheduleId);
}

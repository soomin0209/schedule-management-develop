package com.schedulemanagementdevelop.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String title;
    private String content;

    public Schedule(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public void update(String writer, String title) {
        this.writer = writer;
        this.title = title;
    }
}

package com.lsh.scheduler.module.scheduler.dto;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SchedulerResponseDto {
    private long id;
    private String name;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static SchedulerResponseDto toDto(Scheduler scheduler) {
        return SchedulerResponseDto.builder()
                .id(scheduler.getId())
                .task(scheduler.getTask())
                .name(scheduler.getMember().getName())
                .createdAt(scheduler.getCreatedAt())
                .modifiedAt(scheduler.getModifiedAt())
                .build();
    }
}

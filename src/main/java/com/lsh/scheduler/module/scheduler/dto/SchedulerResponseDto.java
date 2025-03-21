package com.lsh.scheduler.module.scheduler.dto;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SchedulerResponseDto {
    @NotNull
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String task;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
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

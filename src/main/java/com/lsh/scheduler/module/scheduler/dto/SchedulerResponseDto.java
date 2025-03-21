package com.lsh.scheduler.module.scheduler.dto;

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
}

package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerCreateRequestDto {
    @NotNull
    private long memberId;
    @Size(max = 200)
    @NotNull
    private String task;
    @NotNull
    private String password;
}

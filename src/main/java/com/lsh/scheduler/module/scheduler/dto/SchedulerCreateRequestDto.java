package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerCreateRequestDto {
    private Long memberId;
    @Max(200)
    @NotNull
    private String task;
    @NotNull
    private String password;
}

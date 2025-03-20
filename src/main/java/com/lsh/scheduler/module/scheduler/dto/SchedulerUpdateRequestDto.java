package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerUpdateRequestDto {
    @NotNull
    private Long schedulerId;
    @NotNull
    @Max(200)
    private String task;
    @NotNull
    private String memberName;
    @NotNull
    private String password;
}

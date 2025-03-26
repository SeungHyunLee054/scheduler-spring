package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerUpdateRequestDto {
    @NotNull
    @Size(max = 200)
    private String task;
    @NotNull
    private String memberName;
    @NotNull
    private String password;
}

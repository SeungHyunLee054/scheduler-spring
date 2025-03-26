package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerDeleteRequestDto {
    @NotNull
    private String password;
}

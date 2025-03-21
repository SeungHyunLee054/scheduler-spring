package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerDeleteRequestDto {
    @NotNull
    private long schedulerId;
    @NotNull
    private String password;
}

package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerDeleteRequestDto {
    @NotNull
    @NotBlank
    private String password;
}

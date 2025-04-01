package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerCreateRequestDto {
    @NotNull
    @NotBlank
    private long memberId;
    @Size(max = 200)
    @NotNull
    @NotBlank
    private String task;
    @NotNull
    @NotBlank
    private String password;
}

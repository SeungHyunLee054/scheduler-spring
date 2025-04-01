package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerUpdateRequestDto {
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String task;
    @NotNull
    @NotBlank
    private String memberName;
    @NotNull
    @NotBlank
    private String password;
}

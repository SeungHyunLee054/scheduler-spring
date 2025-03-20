package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerCreateRequestDto {
    @NotNull
    private Long memberId;
    @Size(max = 200)
    @NotNull
    private String task;
    @NotNull
    private String password;
}

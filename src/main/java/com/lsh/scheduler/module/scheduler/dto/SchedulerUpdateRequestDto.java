package com.lsh.scheduler.module.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerUpdateRequestDto {
    @NotNull
    private long schedulerId;
    @NotNull
    @Size(max = 200)
    private String task;
    @NotNull
    private String memberName;
    @NotNull
    private String password;
}

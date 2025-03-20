package com.lsh.scheduler.module.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerCreateRequestDto {
    private Long memberId;
    private String task;
    private String password;
}

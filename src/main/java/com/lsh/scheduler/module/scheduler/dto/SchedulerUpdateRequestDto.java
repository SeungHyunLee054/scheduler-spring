package com.lsh.scheduler.module.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerUpdateRequestDto {
    private Long schedulerId;
    private String task;
    private String memberName;
    private String password;
}

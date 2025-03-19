package com.lsh.scheduler.module.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteSchedulerRequestDto {
    private Long id;
    private String password;
}

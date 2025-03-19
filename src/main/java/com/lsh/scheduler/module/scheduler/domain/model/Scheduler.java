package com.lsh.scheduler.module.scheduler.domain.model;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scheduler {
    private Long id;
    private Member member;
    private String task;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static SchedulerResponseDto toDto(Scheduler scheduler) {
        return SchedulerResponseDto.builder()
                .id(scheduler.id)
                .task(scheduler.task)
                .name(scheduler.member.getName())
                .password(scheduler.password)
                .createdAt(scheduler.createdAt)
                .modifiedAt(scheduler.modifiedAt)
                .build();
    }
}

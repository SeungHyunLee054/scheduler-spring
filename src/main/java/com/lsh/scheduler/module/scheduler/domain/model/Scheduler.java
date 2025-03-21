package com.lsh.scheduler.module.scheduler.domain.model;

import com.lsh.scheduler.module.member.domain.model.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Scheduler {
    private Long id;
    private Member member;
    private String task;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}

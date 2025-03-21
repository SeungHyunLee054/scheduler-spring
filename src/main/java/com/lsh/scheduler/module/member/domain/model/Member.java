package com.lsh.scheduler.module.member.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Member {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime modifiedAt;
}

package com.lsh.scheduler.module.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequestDto {
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}

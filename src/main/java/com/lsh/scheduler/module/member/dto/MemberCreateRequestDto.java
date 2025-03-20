package com.lsh.scheduler.module.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequestDto {
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}

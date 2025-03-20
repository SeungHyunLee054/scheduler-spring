package com.lsh.scheduler.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequestDto {
    private String name;
    private String email;
}

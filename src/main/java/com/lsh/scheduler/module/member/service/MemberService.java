package com.lsh.scheduler.module.member.service;

import com.lsh.scheduler.module.member.dto.MemberRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;
import com.lsh.scheduler.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto createMember(MemberRequestDto dto) {
        return memberRepository.save(dto);
    }
}

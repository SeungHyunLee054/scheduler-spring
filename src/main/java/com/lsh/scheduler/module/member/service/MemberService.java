package com.lsh.scheduler.module.member.service;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;
import com.lsh.scheduler.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 작성자 생성
     *
     * @param dto 작성자 id, 이름, 이메일
     * @return 작성자 정보
     */
    public MemberResponseDto createMember(MemberCreateRequestDto dto) {
        return Member.toDto(memberRepository.save(dto));
    }
}

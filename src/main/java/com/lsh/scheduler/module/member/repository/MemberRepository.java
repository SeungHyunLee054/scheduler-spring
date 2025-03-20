package com.lsh.scheduler.module.member.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;

import java.util.Optional;

public interface MemberRepository {

    MemberResponseDto save(MemberCreateRequestDto memberRequestDto);

    Optional<Member> findById(Long id);
}

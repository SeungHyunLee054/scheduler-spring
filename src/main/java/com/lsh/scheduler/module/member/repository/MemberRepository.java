package com.lsh.scheduler.module.member.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;

import java.util.Optional;

public interface MemberRepository {

    MemberResponseDto save(MemberRequestDto memberRequestDto);

    Optional<Member> findById(Long id);
}

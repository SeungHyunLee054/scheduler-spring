package com.lsh.scheduler.module.member.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> save(MemberCreateRequestDto memberRequestDto);

    Optional<Member> findById(Long id);
}

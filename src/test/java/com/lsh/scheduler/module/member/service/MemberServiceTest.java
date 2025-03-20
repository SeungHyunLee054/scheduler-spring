package com.lsh.scheduler.module.member.service;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;
import com.lsh.scheduler.module.member.exception.MemberException;
import com.lsh.scheduler.module.member.exception.MemberExceptionCode;
import com.lsh.scheduler.module.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("유저 생성 성공")
    void success_createMember() {
        // Given
        MemberCreateRequestDto dto = new MemberCreateRequestDto("test", "test@test");

        given(memberRepository.save(any()))
                .willReturn(Optional.of(getMember()));

        // When
        MemberResponseDto result = memberService.createMember(dto);

        // Then
        verify(memberRepository, times(1)).save(any());
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("test", result.getName()),
                () -> assertEquals("test@test", result.getEmail())
        );

    }

    @Test
    @DisplayName("유저 생성 실패")
    void fail_createMember() {
        // Given
        MemberCreateRequestDto dto = new MemberCreateRequestDto("test", "test@test");
        given(memberRepository.save(any()))
                .willReturn(Optional.empty());

        // When
        MemberException exception = assertThrows(MemberException.class, () -> memberService.createMember(dto));

        // Then
        assertEquals(MemberExceptionCode.NOT_FOUND, exception.getErrorCode());

    }

    private static Member getMember() {
        return Member.builder()
                .id(1L)
                .name("test")
                .email("test@test")
                .build();
    }
}
package com.lsh.scheduler.module.scheduler.service;

import com.lsh.scheduler.common.response.ListResponse;
import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.exception.SchedulerException;
import com.lsh.scheduler.module.scheduler.exception.SchedulerExceptionCode;
import com.lsh.scheduler.module.scheduler.repository.SchedulerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private SchedulerRepository schedulerRepository;
    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    @DisplayName("일정 생성 성공")
    void success_saveScheduler() {
        // Given
        SchedulerCreateRequestDto dto = new SchedulerCreateRequestDto(1L, "test", "test");
        Member member = getMember();
        Scheduler scheduler = getScheduler(member);

        given(schedulerRepository.saveScheduler(any()))
                .willReturn(Optional.of(scheduler));

        // When
        SchedulerResponseDto schedulerResponseDto = schedulerService.saveScheduler(dto);

        // Then
        verify(schedulerRepository, times(1)).saveScheduler(any());

        assertAll(
                () -> assertEquals(1L, schedulerResponseDto.getId()),
                () -> assertEquals("test", schedulerResponseDto.getName())
        );
    }

    @Test
    @DisplayName("일정 생성 실패")
    void fail_saveScheduler() {
        // Given
        SchedulerCreateRequestDto dto = new SchedulerCreateRequestDto(1L, "test", "test");

        given(schedulerRepository.saveScheduler(any()))
                .willReturn(Optional.empty());

        // When
        SchedulerException exception = assertThrows(SchedulerException.class,
                () -> schedulerService.saveScheduler(dto));

        // Then
        assertEquals(SchedulerExceptionCode.NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("모든 일정 조회 성공")
    void success_getAllSchedulers() {
        // Given
        String name = "test";
        LocalDate modifiedAt = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        Member member = getMember();
        List<Scheduler> list = List.of(new Scheduler(1L, member, "test", "test", now, now));
        Pageable pageable = PageRequest.of(0, 10);

        given(schedulerRepository.findAll(any(), any(), any()))
                .willReturn(new PageImpl<>(list, pageable, list.size()));

        // When
        ListResponse<SchedulerResponseDto> result =
                schedulerService.getAllSchedulers(name, modifiedAt, pageable);

        // Then
        verify(schedulerRepository, times(1)).findAll(any(), any(), any());

        assertAll(
                () -> assertEquals(1L, result.getContent().get(0).getId()),
                () -> assertEquals("test", result.getContent().get(0).getName())
        );
    }

    @Test
    @DisplayName("id로 일정 조회 성공")
    void success_getSchedulerById() {
        // Given
        given(schedulerRepository.findById(anyLong()))
                .willReturn(Optional.of(getScheduler(getMember())));

        // When
        SchedulerResponseDto result = schedulerService.getSchedulerById(1L);

        // Then
        verify(schedulerRepository, times(1)).findById(anyLong());

        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("test", result.getName()),
                () -> assertEquals("test", result.getTask())
        );
    }

    @Test
    @DisplayName("id로 일정 조회 실패")
    void fail_getSchedulerById() {
        // Given
        given(schedulerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // When
        SchedulerException exception =
                assertThrows(SchedulerException.class, () -> schedulerService.getSchedulerById(1L));

        // Then
        assertEquals(SchedulerExceptionCode.NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("일정 수정 성공")
    void success_updateScheduler() {
        // Given
        SchedulerUpdateRequestDto dto =
                new SchedulerUpdateRequestDto(1L, "test2", "test2", "test");
        LocalDateTime now = LocalDateTime.now();

        given(schedulerRepository.updateScheduler(any()))
                .willReturn(Optional.of(new Scheduler(
                        1L, Member.builder().id(1L).name("test2").build()
                        , "test2", "test", now, now)));

        // When
        SchedulerResponseDto result = schedulerService.updateScheduler(dto);

        // Then
        verify(schedulerRepository, times(1)).updateScheduler(any());
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("test2", result.getName()),
                () -> assertEquals("test2", result.getTask())
        );

    }

    @Test
    @DisplayName("일정 수정 실패")
    void fail_updateScheduler() {
        // Given
        given(schedulerRepository.updateScheduler(any()))
                .willReturn(Optional.empty());

        // When
        SchedulerException exception =
                assertThrows(SchedulerException.class, () -> schedulerService.updateScheduler(null));

        // Then
        assertEquals(SchedulerExceptionCode.NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("일정 삭제 성공")
    void success_deleteScheduler() {
        // Given
        SchedulerDeleteRequestDto dto = new SchedulerDeleteRequestDto(1L, "test");

        given(schedulerRepository.deleteSchedulerById(any()))
                .willReturn(Optional.of(getScheduler(getMember())));

        // When
        SchedulerResponseDto result = schedulerService.deleteScheduler(dto);

        // Then
        verify(schedulerRepository, times(1)).deleteSchedulerById(any());
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("test", result.getName()),
                () -> assertEquals("test", result.getTask())
        );

    }

    @Test
    @DisplayName("일정 삭제 실패")
    void fail_deleteScheduler() {
        // Given
        given(schedulerRepository.deleteSchedulerById(any()))
                .willReturn(Optional.empty());

        // When
        SchedulerException exception =
                assertThrows(SchedulerException.class, () -> schedulerService.deleteScheduler(null));

        // Then
        assertEquals(SchedulerExceptionCode.NOT_FOUND, exception.getErrorCode());

    }


    private static Scheduler getScheduler(Member member) {
        return Scheduler.builder()
                .id(1L)
                .task("test")
                .member(member)
                .build();
    }

    private static Member getMember() {
        return Member.builder()
                .id(1L)
                .name("test")
                .email("test@test")
                .build();
    }
}
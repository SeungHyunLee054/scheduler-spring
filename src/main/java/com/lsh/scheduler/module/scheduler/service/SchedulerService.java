package com.lsh.scheduler.module.scheduler.service;

import com.lsh.scheduler.common.response.ListResponse;
import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.exception.SchedulerException;
import com.lsh.scheduler.module.scheduler.exception.SchedulerExceptionCode;
import com.lsh.scheduler.module.scheduler.repository.SchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final SchedulerRepository schedulerRepository;

    /**
     * 일정을 저장
     *
     * @param dto 일정에 입력할 내용
     * @return 일정의 정보(비밀번호 제외)
     */
    public SchedulerResponseDto saveScheduler(SchedulerCreateRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.saveScheduler(dto)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }

    /**
     * 일정을 조회
     * 입력값에 따라 repository에서 조회하는 메서드가 달라짐
     *
     * @param name       작성자명
     * @param modifiedAt 수정날짜
     * @param pageable   페이지 번호, 페이지 사이즈
     * @return 일정의 정보가 담겨있는 Page의 정보를 List 형태로 한 객체 반환
     */
    public ListResponse<SchedulerResponseDto> getAllSchedulers(String name, LocalDate modifiedAt, Pageable pageable) {
        if (name == null && modifiedAt == null) {
            return ListResponse.fromPage(schedulerRepository.findAll(pageable)
                    .map(Scheduler::toDto));
        } else if (name != null && modifiedAt == null) {
            return ListResponse.fromPage(schedulerRepository.findAllByName(name, pageable)
                    .map(Scheduler::toDto));
        } else if (name == null) {
            return ListResponse.fromPage(schedulerRepository.findAllByModifiedAt(modifiedAt, pageable)
                    .map(Scheduler::toDto));
        } else {
            return ListResponse.fromPage(schedulerRepository.findAllByNameAndModifiedAt(name, modifiedAt, pageable)
                    .map(Scheduler::toDto));
        }
    }

    /**
     * 일정의 id값으로 db에서 조회
     *
     * @param schedulerId 일정 id
     * @return 일정 정보(비밀번호 제외)
     */
    public SchedulerResponseDto getSchedulerById(Long schedulerId) {
        return Scheduler.toDto(schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }

    /**
     * 일정을 수정
     *
     * @param dto 일정 id, 할일, 작성자명, 비밀번호
     * @return 수정완료된 일정 정보(비밀번호 제외)
     */
    public SchedulerResponseDto updateScheduler(SchedulerUpdateRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.updateScheduler(dto)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }

    /**
     * 일정을 삭제
     *
     * @param dto 일정 id, 비밀번호
     * @return 삭제된 일정 정보
     */
    public SchedulerResponseDto deleteScheduler(SchedulerDeleteRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.deleteSchedulerById(dto)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }
}

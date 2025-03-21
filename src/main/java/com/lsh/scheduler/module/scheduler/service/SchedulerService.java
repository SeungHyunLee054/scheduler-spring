package com.lsh.scheduler.module.scheduler.service;

import com.lsh.scheduler.common.response.ListResponse;
import com.lsh.scheduler.common.utils.PasswordUtils;
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
        return ListResponse.fromPage(schedulerRepository.findAll(name, modifiedAt, pageable)
                .map(Scheduler::toDto));
    }

    /**
     * 일정의 id값으로 db에서 조회
     *
     * @param schedulerId 일정 id
     * @return 일정 정보(비밀번호 제외)
     */
    public SchedulerResponseDto getSchedulerById(long schedulerId) {
        if (schedulerId < 1) {
            throw new SchedulerException(SchedulerExceptionCode.WRONG_INPUT);
        }

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
        validatePassword(dto.getSchedulerId(), dto.getPassword());

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
        validatePassword(dto.getSchedulerId(), dto.getPassword());

        return Scheduler.toDto(schedulerRepository.deleteSchedulerById(dto.getSchedulerId())
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }

    /**
     * passwordEncoder로 암호화되어 저장된 비밀번호와 입력한 비밀번호가 일치하는지 검증
     *
     * @param id       일정 id
     * @param password 비밀번호
     */
    private void validatePassword(long id, String password) {
        Scheduler scheduler = schedulerRepository.findById(id)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND));
        if (!PasswordUtils.matches(password, scheduler.getPassword())) {
            throw new SchedulerException(SchedulerExceptionCode.WRONG_PASSWORD);
        }
    }
}

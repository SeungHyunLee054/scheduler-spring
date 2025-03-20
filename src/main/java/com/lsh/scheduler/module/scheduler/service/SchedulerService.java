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

    public SchedulerResponseDto saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto) {
        return Scheduler.toDto(schedulerRepository.saveScheduler(schedulerCreateRequestDto));
    }

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

    public SchedulerResponseDto getSchedulerById(Long schedulerId) {
        return Scheduler.toDto(schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }

    public SchedulerResponseDto updateScheduler(SchedulerUpdateRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.updateScheduler(dto)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }

    public SchedulerResponseDto deleteScheduler(SchedulerDeleteRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.deleteSchedulerById(dto)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND)));
    }
}

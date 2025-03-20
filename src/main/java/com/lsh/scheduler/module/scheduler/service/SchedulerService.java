package com.lsh.scheduler.module.scheduler.service;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.repository.SchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final SchedulerRepository schedulerRepository;

    public SchedulerResponseDto saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto) {
        return schedulerRepository.saveScheduler(schedulerCreateRequestDto);
    }

    public List<SchedulerResponseDto> getAllSchedulers(String name, LocalDate modifiedAt) {
        if (name == null && modifiedAt == null) {
            return schedulerRepository.findAll().stream()
                    .map(Scheduler::toDto)
                    .toList();
        } else if (name != null && modifiedAt == null) {
            return schedulerRepository.findAllByName(name).stream()
                    .map(Scheduler::toDto)
                    .toList();
        } else if (name == null) {
            return schedulerRepository.findAllByModifiedAt(modifiedAt).stream()
                    .map(Scheduler::toDto)
                    .toList();
        } else {
            return schedulerRepository.findAllByNameAndModifiedAt(name, modifiedAt).stream()
                    .map(Scheduler::toDto)
                    .toList();
        }
    }

    public SchedulerResponseDto getSchedulerById(Long schedulerId) {
        return Scheduler.toDto(schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)));
    }

    public SchedulerResponseDto updateScheduler(SchedulerUpdateRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.updateScheduler(dto)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)));
    }

    public SchedulerResponseDto deleteScheduler(SchedulerDeleteRequestDto dto) {
        return Scheduler.toDto(schedulerRepository.deleteSchedulerByIdAndPassword(dto)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)));
    }
}

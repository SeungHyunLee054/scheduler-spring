package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface SchedulerRepository {

    SchedulerResponseDto saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto);

    Optional<Scheduler> findById(long id);

    Page<Scheduler> findAll(Pageable pageable);

    Page<Scheduler> findAllByName(String name,Pageable pageable);

    Page<Scheduler> findAllByModifiedAt(LocalDate modifiedAt,Pageable pageable);

    Page<Scheduler> findAllByNameAndModifiedAt(String name, LocalDate modifiedAt,Pageable pageable);

    Optional<Scheduler> updateScheduler(SchedulerUpdateRequestDto schedulerUpdateRequestDto);

    Optional<Scheduler> deleteSchedulerById(SchedulerDeleteRequestDto schedulerDeleteRequestDto);
}

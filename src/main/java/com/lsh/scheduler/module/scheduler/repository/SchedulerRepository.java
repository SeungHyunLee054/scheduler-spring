package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface SchedulerRepository {

    Optional<Scheduler> saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto);

    Optional<Scheduler> findById(long id);

    Page<Scheduler> findAll(String name, LocalDate modifiedAt, Pageable pageable);

    Optional<Scheduler> updateScheduler(SchedulerUpdateRequestDto schedulerUpdateRequestDto);

    Optional<Scheduler> deleteSchedulerById(long id);
}

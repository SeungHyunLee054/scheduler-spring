package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SchedulerRepository {

    SchedulerResponseDto saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto);

    Optional<Scheduler> findById(long id);

    List<Scheduler> findAll();

    List<Scheduler> findAllByName(String name);

    List<Scheduler> findAllByModifiedAt(LocalDate modifiedAt);

    List<Scheduler> findAllByNameAndModifiedAt(String name, LocalDate modifiedAt);

    Optional<Scheduler> updateScheduler(SchedulerUpdateRequestDto schedulerUpdateRequestDto);

    Optional<Scheduler> deleteSchedulerByIdAndPassword(SchedulerDeleteRequestDto schedulerDeleteRequestDto);
}

package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.DeleteSchedulerRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.UpdateSchedulerRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SchedulerRepository {

    SchedulerResponseDto saveScheduler(SchedulerRequestDto schedulerRequestDto);

    Optional<Scheduler> findById(long id);

    List<Scheduler> findAll();

    List<Scheduler> findAllByName(String name);

    List<Scheduler> findAllByModifiedAt(LocalDate modifiedAt);

    List<Scheduler> findAllByNameAndModifiedAt(String name, LocalDate modifiedAt);

    Optional<Scheduler> updateScheduler(UpdateSchedulerRequestDto updateSchedulerRequestDto);

    Optional<Scheduler> deleteSchedulerByIdAndPassword(DeleteSchedulerRequestDto deleteSchedulerRequestDto);
}

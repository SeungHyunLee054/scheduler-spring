package com.lsh.scheduler.module.scheduler.controller;

import com.lsh.scheduler.common.response.ListResponse;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.exception.SchedulerException;
import com.lsh.scheduler.module.scheduler.exception.SchedulerExceptionCode;
import com.lsh.scheduler.module.scheduler.service.SchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/schedulers")
@RequiredArgsConstructor
public class SchedulerController {
    private final SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<SchedulerResponseDto> createScheduler(
            @Valid @RequestBody SchedulerCreateRequestDto dto) {
        return ResponseEntity.ok(schedulerService.saveScheduler(dto));
    }

    @GetMapping("/{pageIdx}/{pageSize}")
    public ResponseEntity<ListResponse<SchedulerResponseDto>> getAllSchedulers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate modifiedAt,
            @PathVariable Integer pageIdx,
            @PathVariable Integer pageSize) {
        if (pageIdx == null || pageIdx < 0 || pageSize == null || pageSize < 1) {
            throw new SchedulerException(SchedulerExceptionCode.WRONG_INPUT);
        }
        return ResponseEntity
                .ok(schedulerService.getAllSchedulers(name, modifiedAt, PageRequest.of(pageIdx, pageSize)));
    }

    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> getSchedulerById(@PathVariable Long schedulerId) {
        if (schedulerId == null || schedulerId < 1) {
            throw new SchedulerException(SchedulerExceptionCode.WRONG_INPUT);
        }
        return ResponseEntity.ok(schedulerService.getSchedulerById(schedulerId));
    }

    @PutMapping
    public ResponseEntity<SchedulerResponseDto> updateScheduler(@Valid @RequestBody SchedulerUpdateRequestDto dto) {
        return ResponseEntity.ok(schedulerService.updateScheduler(dto));
    }

    @DeleteMapping
    public ResponseEntity<SchedulerResponseDto> deleteScheduler(@Valid @RequestBody SchedulerDeleteRequestDto dto) {
        return ResponseEntity.ok(schedulerService.deleteScheduler(dto));
    }
}

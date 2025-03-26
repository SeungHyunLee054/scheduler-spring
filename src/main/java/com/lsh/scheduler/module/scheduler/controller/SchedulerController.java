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

    @GetMapping
    public ResponseEntity<ListResponse<SchedulerResponseDto>> getAllSchedulers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate modifiedAt,
            @RequestParam int pageIdx,
            @RequestParam int pageSize) {
        if (pageIdx < 0 || pageSize < 1) {
            throw new SchedulerException(SchedulerExceptionCode.WRONG_INPUT);
        }
        return ResponseEntity
                .ok(schedulerService.getAllSchedulers(name, modifiedAt, PageRequest.of(pageIdx, pageSize)));
    }

    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> getSchedulerById(@PathVariable long schedulerId) {
        return ResponseEntity.ok(schedulerService.getSchedulerById(schedulerId));
    }

    @PutMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> updateScheduler(
            @PathVariable long schedulerId,
            @Valid @RequestBody SchedulerUpdateRequestDto dto) {
        return ResponseEntity.ok(schedulerService.updateScheduler(schedulerId, dto));
    }

    @DeleteMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> deleteScheduler(
            @PathVariable long schedulerId,
            @Valid @RequestBody SchedulerDeleteRequestDto dto) {
        return ResponseEntity.ok(schedulerService.deleteScheduler(schedulerId, dto));
    }
}

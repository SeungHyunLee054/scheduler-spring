package com.lsh.scheduler.global.scheduler.controller;

import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.service.SchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedulers")
@RequiredArgsConstructor
public class SchedulerController {
    private final SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<SchedulerResponseDto> addScheduler(
            @Valid @RequestBody SchedulerCreateRequestDto dto) {
        return ResponseEntity.ok(schedulerService.saveScheduler(dto));
    }

    @GetMapping("/{pageIdx}/{pageSize}")
    public ResponseEntity<List<SchedulerResponseDto>> getAllSchedulers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate modifiedAt,
            @PathVariable int pageIdx,
            @PathVariable int pageSize) {
        return ResponseEntity.ok(schedulerService
                .getAllSchedulers(name, modifiedAt, PageRequest.of(pageIdx, pageSize))
                .getContent());
    }

    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> getSchedulerById(@PathVariable Long schedulerId) {
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

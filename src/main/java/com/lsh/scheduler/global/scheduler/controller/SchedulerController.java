package com.lsh.scheduler.global.scheduler.controller;

import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.service.SchedulerService;
import lombok.RequiredArgsConstructor;
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
            @RequestBody SchedulerCreateRequestDto dto) {
        return ResponseEntity.ok()
                .body(schedulerService.saveScheduler(dto));
    }

    @GetMapping
    public ResponseEntity<List<SchedulerResponseDto>> getAllSchedulers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate modifiedAt) {
        return ResponseEntity.ok()
                .body(schedulerService.getAllSchedulers(name, modifiedAt));
    }

    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> getSchedulerById(@PathVariable Long schedulerId) {
        return ResponseEntity.ok()
                .body(schedulerService.getSchedulerById(schedulerId));
    }

    @PutMapping
    public ResponseEntity<SchedulerResponseDto> updateScheduler(@RequestBody SchedulerUpdateRequestDto dto) {
        return ResponseEntity.ok()
                .body(schedulerService.updateScheduler(dto));
    }

    @DeleteMapping
    public ResponseEntity<SchedulerResponseDto> deleteScheduler(@RequestBody SchedulerDeleteRequestDto dto) {
        return ResponseEntity.ok()
                .body(schedulerService.deleteScheduler(dto));
    }
}

package com.lsh.scheduler.module.scheduler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SchedulerExceptionCode {
    NOT_FOUND("일정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    WRONG_INPUT("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String description;
    private final HttpStatus httpStatus;
}

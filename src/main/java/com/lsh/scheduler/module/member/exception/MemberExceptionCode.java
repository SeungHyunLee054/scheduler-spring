package com.lsh.scheduler.module.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode {
    NOT_FOUND("작성자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    ;

    private final String description;
    private final HttpStatus httpStatus;
}

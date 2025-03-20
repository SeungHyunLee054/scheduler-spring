package com.lsh.scheduler.module.member.exception;

import com.lsh.scheduler.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberException extends BaseException {
    private final MemberExceptionCode errorCode;
    private final HttpStatus httpStatus;
    private final String description;

    public MemberException(MemberExceptionCode errorCode) {
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.description = errorCode.getDescription();
    }
}

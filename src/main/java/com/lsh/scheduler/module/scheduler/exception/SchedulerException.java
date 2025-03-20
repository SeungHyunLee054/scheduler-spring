package com.lsh.scheduler.module.scheduler.exception;

import com.lsh.scheduler.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class SchedulerException extends BaseException {
    private final SchedulerExceptionCode errorCode;
    private final HttpStatusCode httpStatus;
    private final String description;

    public SchedulerException(SchedulerExceptionCode errorCode) {
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
        this.httpStatus = errorCode.getHttpStatus();
    }
}

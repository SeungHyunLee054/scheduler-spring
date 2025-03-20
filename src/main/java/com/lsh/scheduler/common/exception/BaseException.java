package com.lsh.scheduler.common.exception;

import org.springframework.http.HttpStatusCode;

public abstract class BaseException extends RuntimeException {
    public abstract Enum<?> getErrorCode();

    public abstract HttpStatusCode getHttpStatus();

    public abstract String getDescription();
}

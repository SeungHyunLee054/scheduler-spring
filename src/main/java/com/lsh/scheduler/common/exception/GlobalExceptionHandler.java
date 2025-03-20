package com.lsh.scheduler.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> CustomExceptionHandler(BaseException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .errorCode(e.getErrorCode().name())
                        .description(e.getDescription())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> inputValidationExceptionHandler(BindingResult result) {
        return ResponseEntity.badRequest()
                .body(result.getFieldErrors()
                        .stream()
                        .map(e -> ErrorResponse.builder()
                                .errorCode(e.getCode())
                                .description(e.getDefaultMessage())
                                .build())
                        .toList());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlExceptionHandler() {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.name())
                        .description("잘못된 요청입니다.")
                        .build());
    }
}

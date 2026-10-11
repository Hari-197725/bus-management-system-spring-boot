package com.project.bus_reservation.exception;

import com.project.bus_reservation.exception.builder.ExceptionResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception exception) {
        return ExceptionResponseBuilder.build(exception);
    }
}
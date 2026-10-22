package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.builder.ExceptionResponseBuilder;
import com.project.bus_reservation.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
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

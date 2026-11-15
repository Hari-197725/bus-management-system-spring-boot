package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.custom.ForbiddenException;
import com.project.bus_reservation.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ForbiddenExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        ForbiddenException forbiddenException = (ForbiddenException) exception;
        ErrorResponse errorResponse = new ErrorResponse(
                forbiddenException.getCode().name(),
                forbiddenException.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}


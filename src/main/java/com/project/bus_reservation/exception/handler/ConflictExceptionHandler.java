package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.ConflictException;
import com.project.bus_reservation.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ConflictExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        ConflictException conflictException = (ConflictException) exception;

        ErrorResponse errorResponse = new ErrorResponse(
                conflictException.getErrorCode(),
                conflictException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
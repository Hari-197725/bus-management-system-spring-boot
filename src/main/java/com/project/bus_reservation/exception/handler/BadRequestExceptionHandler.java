package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.custom.BadRequestException;
import com.project.bus_reservation.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequestExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        BadRequestException badRequestException = (BadRequestException) exception;

        ErrorResponse errorResponse = new ErrorResponse(
                badRequestException.getCode(),
                badRequestException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

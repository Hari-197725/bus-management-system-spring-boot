package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.BadRequestException;
import com.project.bus_reservation.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequestExceptionHandler implements ExceptionHandlerStrategy{

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        BadRequestException badRequestException = (BadRequestException) exception;

        ErrorResponse errorResponse = new ErrorResponse(
                badRequestException.getErrorCode(),
                badRequestException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
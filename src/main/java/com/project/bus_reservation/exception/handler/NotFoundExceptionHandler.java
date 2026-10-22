package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NotFoundExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        NotFoundException notFoundException = (NotFoundException) exception;

        ErrorResponse errorResponse = new ErrorResponse(
                notFoundException.getCode(),
                notFoundException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}

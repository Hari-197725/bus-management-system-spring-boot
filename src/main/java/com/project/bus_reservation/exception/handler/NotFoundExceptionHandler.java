package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.ErrorResponse;
import com.project.bus_reservation.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NotFoundExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        NotFoundException notFoundException = (NotFoundException) exception;

        ErrorResponse errorResponse = new ErrorResponse(
                notFoundException.getErrorCode(),
                notFoundException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


}

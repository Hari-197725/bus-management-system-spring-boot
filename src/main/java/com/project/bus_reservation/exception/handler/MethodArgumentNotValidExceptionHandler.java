package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

public class MethodArgumentNotValidExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        if (errorMessage.isEmpty()) {
            errorMessage = "Validation failed for request";
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_REQUEST.name(),
                errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}


package com.project.bus_reservation.exception.handler;

import com.project.bus_reservation.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandlerStrategy {
    ResponseEntity<ErrorResponse> handle(Exception exception);
}

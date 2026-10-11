package com.project.bus_reservation.exception.builder;

import com.project.bus_reservation.exception.BadRequestException;
import com.project.bus_reservation.exception.ConflictException;
import com.project.bus_reservation.exception.ErrorResponse;
import com.project.bus_reservation.exception.NotFoundException;
import com.project.bus_reservation.exception.handler.BadRequestExceptionHandler;
import com.project.bus_reservation.exception.handler.ConflictExceptionHandler;
import com.project.bus_reservation.exception.handler.ExceptionHandlerStrategy;
import com.project.bus_reservation.exception.handler.NotFoundExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseBuilder {
    private static final Map<Class<? extends Exception>, ExceptionHandlerStrategy> strategies = new HashMap<>();

    static {
        strategies.put(NotFoundException.class, new NotFoundExceptionHandler());
        strategies.put(BadRequestException.class, new BadRequestExceptionHandler());
        strategies.put(ConflictException.class, new ConflictExceptionHandler());
    }

    public static ResponseEntity<ErrorResponse> build(Exception exception) {
        ExceptionHandlerStrategy handlerStrategy = strategies.get(exception.getClass());
        if (handlerStrategy != null) {
            return handlerStrategy.handle(exception);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR", "An unexpected error occurred"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

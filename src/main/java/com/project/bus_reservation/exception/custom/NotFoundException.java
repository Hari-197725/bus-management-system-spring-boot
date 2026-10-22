package com.project.bus_reservation.exception.custom;

import com.project.bus_reservation.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final ErrorCode Code;

    public NotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.Code = errorCode;
    }

    public NotFoundException(Throwable cause, ErrorCode errorCode, String message) {
        super(message, cause);
        this.Code = errorCode;
    }
}
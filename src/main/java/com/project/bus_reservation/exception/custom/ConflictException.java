package com.project.bus_reservation.exception.custom;

import com.project.bus_reservation.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final ErrorCode Code;

    public ConflictException(ErrorCode errorCode, String message) {
        super(message);
        this.Code = errorCode;
    }

    public ConflictException(Throwable cause, ErrorCode errorCode, String message) {
        super(message, cause);
        this.Code = errorCode;
    }
}

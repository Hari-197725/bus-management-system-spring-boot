package com.project.bus_reservation.exception.custom;

import com.project.bus_reservation.exception.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final ErrorCode code;

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode;
    }
}


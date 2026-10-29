package com.project.bus_reservation.exception.response;

import com.project.bus_reservation.exception.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private ErrorCode Code;
    private String message;
}

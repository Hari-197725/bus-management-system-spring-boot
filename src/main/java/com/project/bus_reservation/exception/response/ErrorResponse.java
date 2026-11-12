package com.project.bus_reservation.exception.response;

import com.project.bus_reservation.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "Standard error response returned for all API errors")
public class ErrorResponse {
    @Schema(description = "Application-specific error code", implementation = String.class)
    private ErrorCode code;

    @Schema(description = "Human-readable error message")
    private String message;
}
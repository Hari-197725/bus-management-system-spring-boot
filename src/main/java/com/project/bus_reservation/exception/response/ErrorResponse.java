package com.project.bus_reservation.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "Standard error response returned when an API request fails")
public class ErrorResponse {

    @Schema(description = "Application-specific error code")
    private String Code;

    @Schema(description = "Detailed error message describing what went wrong")
    private String message;
}

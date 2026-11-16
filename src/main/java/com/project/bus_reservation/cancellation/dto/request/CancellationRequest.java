package com.project.bus_reservation.cancellation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class CancellationRequest {
    @NotEmpty(message = "At least one seat number is required for cancellation")
    private List<Integer> seatNumbers;
}
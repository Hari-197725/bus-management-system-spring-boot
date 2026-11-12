package com.project.bus_reservation.cancellation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "Request payload for cancelling seats from a booking")
public class CancellationRequest {
    @Schema(description = "List of seat numbers to cancel from the booking", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> seatNumbers;
}

package com.project.bus_reservation.booking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NotNull
@Schema(description = "Request payload for creating a new booking")
public class BookingCreateRequest {
    @Schema(description = "Total booking amount", example = "1500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @Schema(description = "ID of the route for the booking", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long routeId;

    @Schema(description = "ID of the bus for the booking", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long busId;

    @Schema(description = "Number of seats to book", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer seatCount;

    @Schema(description = "List of passenger-seat assignments", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<passengerCreateRequest> passengers;

    @Getter
    @NotNull
    @Schema(description = "Passenger-seat assignment for a booking")
    public static class passengerCreateRequest {
        @Schema(description = "ID of the passenger", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long passengerId;

        @Schema(description = "ID of the seat to assign to the passenger", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long seatId;
    }
}

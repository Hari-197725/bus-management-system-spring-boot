package com.project.bus_reservation.bus.dto.request;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.seats.enums.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "Request payload for creating a new bus")
public class BusCreateRequest {
    @NotNull
    @Positive
    @Min(6)
    @Schema(description = "Unique bus registration number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer busNumber;

    @NotBlank
    @Schema(description = "Name/display name of the bus", requiredMode = Schema.RequiredMode.REQUIRED)
    private String busName;

    @NotNull
    @Schema(description = "Type of the bus", requiredMode = Schema.RequiredMode.REQUIRED)
    private BusType busType;

    @NotNull
    @Min(18)
    @Max(30)
    @Schema(description = "Total number of seats in the bus (18-30)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalSeats;

    @Schema(description = "ID of the route to assign to this bus")
    private Long routeId;

    @NotNull
    @Schema(description = "List of seat configurations for the bus", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SeatRequest> seats;

    @Getter
    @Schema(description = "Seat configuration for a bus")
    public static class SeatRequest {

        @NotNull
        @Positive
        @Schema(description = "Seat number", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer seatNumber;

        @NotNull
        @Schema(description = "Type of the seat", requiredMode = Schema.RequiredMode.REQUIRED)
        private SeatType seatType;
    }
}

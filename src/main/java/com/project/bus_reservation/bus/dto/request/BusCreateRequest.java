package com.project.bus_reservation.bus.dto.request;

import com.project.bus_reservation.bus.enums.BusType;
import com.project.bus_reservation.seats.enums.SeatType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class BusCreateRequest {
    @NotNull
    @Positive
    @Min(6)
    private Integer busNumber;

    @NotBlank
    private String busName;

    @NotNull
    private BusType busType;

    @NotNull
    @Min(18)
    @Max(30)
    private Integer totalSeats;

    private String routeId;

    @NotEmpty(message = "Seats list cannot be empty")
    private List<@Valid SeatRequest> seats;

    @Getter
    public static class SeatRequest {

        @NotNull(message = "Seat number is required")
        @Positive(message = "Seat number must be positive")
        private Integer seatNumber;

        @NotNull(message = "Seat type is required")
        private SeatType seatType;
    }
}
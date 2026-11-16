package com.project.bus_reservation.booking.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.List;

@Getter
public class BookingCreateRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Route ID is required")
    private String routeId;

    @NotBlank(message = "Bus ID is required")
    private String busId;

    @NotNull(message = "Seat count is required")
    @Positive(message = "Seat count must be positive")
    private Integer seatCount;

    @NotEmpty(message = "Passengers list cannot be empty")
    private List<@Valid PassengerBookingRequest> passengers;

    @Getter
    public static class PassengerBookingRequest {

        @NotBlank(message = "Passenger ID is required")
        private String passengerId;

        @NotBlank(message = "Seat ID is required")
        private String seatId;
    }
}
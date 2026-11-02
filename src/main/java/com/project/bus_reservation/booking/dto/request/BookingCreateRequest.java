package com.project.bus_reservation.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
@NotNull
public class BookingCreateRequest {
    private Double amount;
    private String routeId;
    private String busId;
    private Integer seatCount;
    private List<passengerCreateRequest> passengers;

    @Getter
    @NotNull
    public static class passengerCreateRequest {
        private String passengerId;
        private String seatId;
    }
}
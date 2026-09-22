package com.project.bus_reservation.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NotNull
public class BookingCreateRequest {
    private BigDecimal amount;
    private Long routeId;
    private Long busId;
    private Integer seatCount;
    private List<passengerCreateRequest> passengers;

    @Getter
    @NotNull
    public static class passengerCreateRequest {
        private Long passengerId;
        private Long seatId;
    }
}
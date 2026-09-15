package com.project.bus_reservation.booking.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class BookingCreateRequest {
    private BigDecimal amount;
    private Long routeId;
    private Long busId;
    private Integer seatCount;
    private List<passengerCreateRequest> passengers;

    @Getter
    public static class passengerCreateRequest {
        private Long passengerId;
        private Long seatId;
    }
}
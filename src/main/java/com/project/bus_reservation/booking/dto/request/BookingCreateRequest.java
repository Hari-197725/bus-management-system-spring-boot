package com.project.bus_reservation.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BookingCreateRequest {
    @NotNull
    @Positive
    private BigDecimal totalAmount;

    @NotNull
    private Long busTripId;
}
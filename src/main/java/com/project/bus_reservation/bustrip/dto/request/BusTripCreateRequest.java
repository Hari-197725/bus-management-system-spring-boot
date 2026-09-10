package com.project.bus_reservation.bustrip.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BusTripCreateRequest {
    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    private Long busId;

    @NotNull
    private Long routeId;
}

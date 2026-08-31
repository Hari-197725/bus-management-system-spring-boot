package com.project.bus_reservation.bustransfer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusTransferUpdateRequest {
    @NotNull
    private Long busId;

    @NotNull
    private Long transferOperatorId;

    private Long routeId;
}
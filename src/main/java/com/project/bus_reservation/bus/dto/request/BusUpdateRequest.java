package com.project.bus_reservation.bus.dto.request;

import com.project.bus_reservation.bus.enums.BusStatus;
import com.project.bus_reservation.bus.enums.BusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request payload for updating bus details")
public class BusUpdateRequest {
    @Schema(description = "Updated name of the bus", example = "Volvo Sleeper")
    private String busName;

    @Schema(description = "Updated type of the bus", example = "SEMI_SLEEPER")
    private BusType busType;

    @Schema(description = "Updated status of the bus", example = "ACTIVE")
    private BusStatus busStatus;
}

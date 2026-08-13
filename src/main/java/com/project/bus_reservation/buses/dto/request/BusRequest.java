package com.project.bus_reservation.buses.dto.request;

import com.project.bus_reservation.buses.enums.BusStatus;
import com.project.bus_reservation.buses.enums.BusType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusRequest {

    @NotNull
    @Positive
    @Min(6)
    private Integer busNumber;

    @NotBlank
    private String busName;

    @NotNull
    private BusType busType;

    @NotNull
    private Long operatorId;

    @NotNull
    @Min(18)
    private Integer totalSeats;

    @NotNull
    private BusStatus status;
}
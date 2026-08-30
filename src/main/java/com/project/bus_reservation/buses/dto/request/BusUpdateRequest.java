package com.project.bus_reservation.buses.dto.request;

import com.project.bus_reservation.buses.enums.BusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusUpdateRequest {

    private BusType busType;
    private Long operatorId;
    private Long routeId;
}
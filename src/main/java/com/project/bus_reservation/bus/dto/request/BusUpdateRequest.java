package com.project.bus_reservation.bus.dto.request;

import com.project.bus_reservation.bus.enums.BusStatus;
import com.project.bus_reservation.bus.enums.BusType;

public class BusUpdateRequest {
    private String busName;
    private BusType busType;
    private BusStatus busStatus;
}

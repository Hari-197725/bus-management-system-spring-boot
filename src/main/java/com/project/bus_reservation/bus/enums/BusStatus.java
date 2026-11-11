package com.project.bus_reservation.bus.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Bus operational status: ACTIVE (1), INACTIVE (2)")
public enum BusStatus {
    ACTIVE(1),
    INACTIVE(2);

    private final int value;

    BusStatus(int value) {
        this.value = value;
    }

    public static BusStatus fromValue(int value) {
        for (BusStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid bus status: " + value);
    }
}

package com.project.bus_reservation.buses.enums;

import lombok.Getter;

@Getter
public enum BusType {
    SLEEPER(1),
    SEMI_SLEEPER(2),
    SEATER(3);

    private final int value;

    BusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BusType fromValue(int value) {
        for (BusType type : BusType.values()) {
            if (type.value == value) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid bus type: " + value);
    }
}
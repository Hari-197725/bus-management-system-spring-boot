package com.project.bus_reservation.enums;

public enum BusStatus {
    ACTIVE(1),
    INACTIVE(2),
    UNDER_MAINTENANCE(3);

    private final int value;

    private BusStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
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

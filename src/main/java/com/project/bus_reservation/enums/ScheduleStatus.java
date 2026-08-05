package com.project.bus_reservation.enums;

public enum ScheduleStatus {
    BOARDING(1), DEPARTED(2), IN_TRANSIT(3), ARRIVED(4), CANCELLED(5);

    private final int value;

    private ScheduleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public ScheduleStatus fromValue(int value) {
        for (ScheduleStatus status : ScheduleStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid Schedule status: " + value);
    }
}
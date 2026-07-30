package com.project.bus_reservation.enums;

public enum ScheduleStatus {
    SCHEDULED(1), BOARDING(2), DEPARTED(3), IN_TRANSIT(4), ARRIVED(5), CANCELLED(6);

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
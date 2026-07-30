package com.project.bus_reservation.enums;

import com.project.bus_reservation.models.Booking;

public enum BookingStatus {
    PENDING(1), CONFIRMED(2), CANCELLED(3);

    private final int value;

    private BookingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BookingStatus fromValue(int value) {
        for (BookingStatus status : BookingStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid Booking status: " + value);
    }
}
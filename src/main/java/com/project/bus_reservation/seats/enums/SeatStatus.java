package com.project.bus_reservation.seats.enums;

public enum SeatStatus {
    AVAILABLE(1), BOOKED(2);

    private final int value;

    private SeatStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SeatStatus fromValue(int value) {
        for (SeatStatus status : SeatStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid Seat status: " + value);
    }
}
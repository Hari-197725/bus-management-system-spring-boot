package com.project.bus_reservation.enums;

public enum SeatType {
    WINDOW(1), AISLE(2), LOWER_BERTH(3), UPPER_BERTH(4);

    private final int value;

    private SeatType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SeatType fromValue(int value) {
        for (SeatType type : SeatType.values()) {
            if (type.value == value) {
                return type;

            }
        }

        throw new IllegalArgumentException("Invalid Seat type: " + value);
    }
}
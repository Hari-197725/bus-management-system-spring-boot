package com.project.bus_reservation.passenger.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Gender of the passenger: MALE (1), FEMALE (2), OTHER (3)")
public enum Gender {
    MALE(1), FEMALE(2), OTHER(3);

    private final int value;

    private Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.value == value) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid Gender selected: " + value);
    }
}
package com.project.bus_reservation.passenger.dto.request;

import com.project.bus_reservation.passenger.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PassengerCreateRequest {
    @NotNull
    private Integer age;

    @NotNull
    private Gender gender;

    @NotNull
    @Size(max = 20)
    private String name;
}
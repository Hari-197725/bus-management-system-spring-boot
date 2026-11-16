package com.project.bus_reservation.passenger.dto.request;

import com.project.bus_reservation.passenger.enums.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PassengerCreateRequest {
    @NotNull(message = "Age is required")
    @Min(value = 3, message = "Age must be at least 3")
    @Max(value = 100, message = "Age cannot be greater than 100")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;
}
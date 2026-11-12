package com.project.bus_reservation.passenger.dto.request;

import com.project.bus_reservation.passenger.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "Request payload for creating a new passenger")
public class PassengerCreateRequest {
    @NotNull
    @Schema(description = "Age of the passenger", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer age;

    @NotNull
    @Schema(description = "Gender of the passenger", requiredMode = Schema.RequiredMode.REQUIRED)
    private Gender gender;

    @NotNull
    @Size(max = 20)
    @Schema(description = "Name of the passenger (max 20 characters)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}

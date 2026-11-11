package com.project.bus_reservation.passenger.dto.response;

import com.project.bus_reservation.passenger.enums.Gender;
import com.project.bus_reservation.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Passenger details response")
public class PassengerResponse {
    @Schema(description = "Unique identifier of the passenger", example = "1")
    private Long id;

    @Schema(description = "Age of the passenger", example = "25")
    private Integer age;

    @Schema(description = "Gender of the passenger")
    private Gender gender;

    @Schema(description = "Name of the passenger", example = "Rahul Kumar")
    private String name;

    @Schema(description = "User account this passenger belongs to")
    private UserResponse userResponse;
}

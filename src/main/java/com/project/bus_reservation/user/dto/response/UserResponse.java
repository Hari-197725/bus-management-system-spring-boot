package com.project.bus_reservation.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "User details response")
public class UserResponse {

    @Schema(description = "Unique identifier of the user")
    private Long id;

    @Schema(description = "Full name of the user")
    private String name;

    @Schema(description = "Email address of the user")
    private String email;

    @Schema(description = "Phone number of the user")
    private String phone;
}

package com.project.bus_reservation.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "Request payload for creating a new user")
public class UserCreateRequest {
    @NotBlank
    @Schema(description = "Full name of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Schema(description = "Email address of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "Phone number of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;
}

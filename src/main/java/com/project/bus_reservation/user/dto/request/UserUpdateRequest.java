package com.project.bus_reservation.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Request payload for partially updating a user")
public class UserUpdateRequest {
    @Schema(description = "Updated name of the user", example = "Jane Doe")
    private String name;

    @Schema(description = "Updated email of the user", example = "jane.doe@example.com")
    private String email;
}

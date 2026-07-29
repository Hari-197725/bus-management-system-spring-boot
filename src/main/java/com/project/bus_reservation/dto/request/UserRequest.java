package com.project.bus_reservation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    private String phone;
}

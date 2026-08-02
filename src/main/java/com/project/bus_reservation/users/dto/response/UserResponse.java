package com.project.bus_reservation.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
}

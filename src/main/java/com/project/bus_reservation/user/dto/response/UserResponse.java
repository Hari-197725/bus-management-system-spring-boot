package com.project.bus_reservation.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
}

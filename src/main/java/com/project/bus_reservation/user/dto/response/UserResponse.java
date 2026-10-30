package com.project.bus_reservation.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {

    private String firebaseUid;
    private String name;
    private String email;
    private String phone;
}

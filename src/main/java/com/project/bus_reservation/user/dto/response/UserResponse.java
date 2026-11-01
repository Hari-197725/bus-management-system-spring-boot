package com.project.bus_reservation.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private String userId;
    private String name;
    private String email;
    private String phone;

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }
}

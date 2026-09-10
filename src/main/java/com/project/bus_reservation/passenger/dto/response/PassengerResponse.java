package com.project.bus_reservation.passenger.dto.response;

import com.project.bus_reservation.passenger.enums.Gender;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponse {
    private Long id;
    private Integer age;
    private Gender gender;
    private String name;
    private UserResponse user;
}
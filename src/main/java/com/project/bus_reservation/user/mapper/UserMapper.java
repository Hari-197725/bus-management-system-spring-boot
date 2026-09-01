package com.project.bus_reservation.user.mapper;

import com.project.bus_reservation.user.dto.request.UserRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhone())
                .build();
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber());
    }
}
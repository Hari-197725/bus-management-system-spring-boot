package com.project.bus_reservation.users.mapper;

import com.project.bus_reservation.users.dto.request.UserRequest;
import com.project.bus_reservation.users.dto.response.UserResponse;
import com.project.bus_reservation.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhone());
        return user;
    }
}

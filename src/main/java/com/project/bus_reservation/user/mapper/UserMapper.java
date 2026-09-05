package com.project.bus_reservation.user.mapper;

import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toUserEntity(UserCreateRequest userCreateRequest) {
//        ask about this to hari

//        return User.builder()
//                .name(userRequest.getName())
//                .phoneNumber(userRequest.getPhone())
//                .build();

        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPhoneNumber(userCreateRequest.getPhoneNumber());

        return user;
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber());
    }
}
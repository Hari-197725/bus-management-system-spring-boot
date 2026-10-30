package com.project.bus_reservation.user.mapper;

import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.request.UserUpdateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public static User toUserEntity(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setFirebaseUid(userCreateRequest.getFirebaseUid());
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPhoneNumber(userCreateRequest.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User toUpdateUserEntity(User user, UserUpdateRequest userUpdateRequest) {
        user.setName(userUpdateRequest.getName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getFirebaseUid(), user.getName(), user.getEmail(), user.getPhoneNumber());
    }
}
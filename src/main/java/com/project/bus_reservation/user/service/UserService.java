package com.project.bus_reservation.user.service;

import com.project.bus_reservation.exception.ConflictException;
import com.project.bus_reservation.exception.NotFoundException;
import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.request.UserUpdateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.mapper.UserMapper;
import com.project.bus_reservation.user.repository.UserPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserPostgresRepository userPostgresRepository;

    public void createUsers(UserCreateRequest userCreateRequest) {
        if (userPostgresRepository.existsByPhoneNumber(userCreateRequest.getPhoneNumber())) {
            throw new ConflictException("PHONE_NUMBER_ALREADY_EXISTS", "Phone number already exists");
        }
        userPostgresRepository.save(UserMapper.toUserEntity(userCreateRequest));
    }

    public List<UserResponse> getAllUsers() {
        List<User> userList = userPostgresRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();

        for (User user : userList) {
            userResponseList.add(UserMapper.toUserResponse(user));
        }

        return userResponseList;
    }

    public UserResponse getUsersById(Long userId) {
        User user = userPostgresRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found with id: " + userId));
        return UserMapper.toUserResponse(user);
    }

    public void updateUserByUserId(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userPostgresRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found with id: " + userId));

        userPostgresRepository.save(UserMapper.toUpdateUserEntity(user, userUpdateRequest));
    }
}
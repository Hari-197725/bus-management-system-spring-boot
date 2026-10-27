package com.project.bus_reservation.user.service;

import com.project.bus_reservation.exception.custom.ConflictException;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.request.UserUpdateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.mapper.UserMapper;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    public void createUsers(UserCreateRequest userCreateRequest) {
        if (usersRepository.findByPhoneNumber(userCreateRequest.getPhoneNumber()).block() != null) {
            throw new ConflictException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS, "Phone number already exists");
        }
        User user = UserMapper.toUserEntity(userCreateRequest);
        if (user.getId() == null) {
            user.setId(String.valueOf(System.currentTimeMillis()));
            user.setCreatedAt(LocalDateTime.now().toString());
        }
        user.setUpdatedAt(LocalDateTime.now().toString());
        usersRepository.save(user).block();
    }

    public List<UserResponse> getAllUsers() {
        List<User> userList = usersRepository.findAll().collectList().block();
        List<UserResponse> userResponseList = new ArrayList<>();

        if (userList != null) {
            for (User user : userList) {
                userResponseList.add(UserMapper.toUserResponse(user));
            }
        }

        return userResponseList;
    }

    public UserResponse getUsersById(Long userId) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }
        return UserMapper.toUserResponse(user);
    }

    public void updateUserByUserId(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        User updatedUser = UserMapper.toUpdateUserEntity(user, userUpdateRequest);
        updatedUser.setUpdatedAt(LocalDateTime.now().toString());
        usersRepository.save(updatedUser).block();
    }
}
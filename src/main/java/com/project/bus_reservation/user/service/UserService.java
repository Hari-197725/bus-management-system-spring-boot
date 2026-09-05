package com.project.bus_reservation.user.service;

import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.mapper.UserMapper;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

    public void createUsers(UserCreateRequest userCreateRequest) {
        usersRepository.save(UserMapper.toUserEntity(userCreateRequest));
    }

    public List<UserResponse> getAllUsers() {
        List<User> userList = usersRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();

        for (User user : userList) {
            userResponseList.add(UserMapper.toUserResponse(user));
        }

        return userResponseList;
    }

    public UserResponse getUsersById(Long userId) {
        User user = usersRepository.getReferenceById(userId);
        return UserMapper.toUserResponse(user);
    }

    public void deleteUsersById(Long userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));

        usersRepository.delete(user);
    }
}

//    Normal service methods.

//    public Users createUsers(Users users) {
//        return usersRepository.save(users);
//    }

//    public User getUsersById(Long id) {
//        return usersRepository.getReferenceById(id);
//    }

//    public User updateUsersById(User users) {
//        return usersRepository.save(users);
//    }

//    public void deleteUsersById(Long id) {
//        usersRepository.deleteById(id);
//    }
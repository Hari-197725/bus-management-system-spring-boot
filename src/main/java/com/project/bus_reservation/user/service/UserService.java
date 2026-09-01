package com.project.bus_reservation.user.service;

import com.project.bus_reservation.user.dto.request.UserRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.mapper.UserMapper;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

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

    public void createUsers(UserRequest userRequest) {
        usersRepository.save(UserMapper.toEntity(userRequest));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = usersRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : users) {
            userResponseList.add(UserMapper.toResponse(user));
        }

        return userResponseList;
    }


    public UserResponse getUsersById(Long id) {
        User user = usersRepository.getReferenceById(id);
        return userMapper.toResponse(user);
    }


    public UserResponse updateUsersById(Long id, UserRequest userRequest) {
        User user = usersRepository.getReferenceById(id);
        user.updateUser(userRequest);
        User updatedUser = usersRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }


    public void deleteUsersById(Long id) {
        usersRepository.deleteById(id);
    }
}
package com.project.bus_reservation.users.service;

import com.project.bus_reservation.users.dto.request.UserRequest;
import com.project.bus_reservation.users.dto.response.UserResponse;
import com.project.bus_reservation.users.entity.User;
import com.project.bus_reservation.users.mapper.UserMapper;
import com.project.bus_reservation.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper  userMapper;

//    public Users createUsers(Users users) {
//        return usersRepository.save(users);
//    }

    public void createUsers(UserRequest userRequest) {
        usersRepository.save(userMapper.toEntity(userRequest));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = usersRepository.findAll();
//        return users
//                .stream()
//                .map(user -> userMapper.toResponse(user))
//                .toList();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : users) {
            userResponseList.add(userMapper.toResponse(user));
        }
        return userResponseList;
    }

    public User getUsersById(Long id) {
        return usersRepository.getReferenceById(id);
    }

    public User updateUsersById(User users) {
        return usersRepository.save(users);
    }

    public void deleteUsersById(Long id) {
        usersRepository.deleteById(id);
    }

}
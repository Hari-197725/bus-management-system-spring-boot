package com.project.bus_reservation.service;

import com.project.bus_reservation.dto.response.UserResponse;
import com.project.bus_reservation.models.Users;
import com.project.bus_reservation.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users createUsers(Users users) {
        return usersRepository.save(users);
    }

    public List<UserResponse> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (Users user : users) {
            userResponseList.add(new UserResponse(user.getId(), user.getName(), user.getEmail()));
        }
        return userResponseList;
    }

    public Users getUsersById(Long id) {
        return usersRepository.getReferenceById(id);
    }

    public Users updateUsersById(Users users) {
        return usersRepository.save(users);
    }

    public void deleteUsersById(Long id) {
        usersRepository.deleteById(id);
    }

}
package com.project.bus_reservation.service;

import com.project.bus_reservation.models.Users;
import com.project.bus_reservation.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users createUsers(Users users) {
        return usersRepository.save(users);
    }
}

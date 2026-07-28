package com.project.bus_reservation.controller;

import com.project.bus_reservation.models.Users;
import com.project.bus_reservation.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService userService;

    @PostMapping("/create")
    public ResponseEntity<Users> createUsers(@Valid @RequestBody Users users) {
        return new ResponseEntity<>(userService.createUsers(users), HttpStatus.CREATED);
    }
}

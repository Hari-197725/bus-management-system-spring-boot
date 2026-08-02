package com.project.bus_reservation.controller;

import com.project.bus_reservation.dto.request.UserRequest;
import com.project.bus_reservation.dto.response.UserResponse;
import com.project.bus_reservation.models.User;
import com.project.bus_reservation.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService userService;

//    Normal post mapping method
//    @PostMapping("/create")
//    public ResponseEntity<Users> createUsers(@Valid @RequestBody Users users) {
//        return new ResponseEntity<>(userService.createUsers(users), HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> createUsers(@Valid @RequestBody UserRequest userRequest) {
        userService.createUsers(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUsersById(@PathVariable Long id, @RequestBody User users) {
        return new ResponseEntity<>(userService.updateUsersById(users), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUsersById(@PathVariable Long id) {
        userService.deleteUsersById(id);
    }
}
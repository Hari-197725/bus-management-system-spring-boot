package com.project.bus_reservation.user.controller;

import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUsers(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createUsers(userCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUsersById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteUsersById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

//Normal mapping method

//    @PostMapping("/create")
//    public ResponseEntity<Users> createUsers(@Valid @RequestBody Users users) {
//        return new ResponseEntity<>(userService.createUsers(users), HttpStatus.CREATED);
//    }


//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUsersById(@PathVariable Long id) {
//        return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUsersById(@PathVariable Long id, @RequestBody User users) {
//        return new ResponseEntity<>(userService.updateUsersById(users), HttpStatus.OK);
//    }


//    @DeleteMapping("/{id}")
//    public void deleteUsersById(@PathVariable Long id) {
//        userService.deleteUsersById(id);
//    }
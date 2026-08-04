package com.project.bus_reservation.users.controller;

import com.project.bus_reservation.users.dto.request.UserRequest;
import com.project.bus_reservation.users.dto.response.UserResponse;
import com.project.bus_reservation.users.entity.User;
import com.project.bus_reservation.users.service.UsersService;
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
    public ResponseEntity<UserResponse> getUsersById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUsersById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.updateUsersById(id, userRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsersById(@PathVariable Long id) {
        userService.deleteUsersById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
package com.project.bus_reservation.controller;

import com.project.bus_reservation.dto.request.UserRequest;
import com.project.bus_reservation.dto.response.UserResponse;
import com.project.bus_reservation.models.Users;
import com.project.bus_reservation.service.UsersService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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

    @PostMapping("/create")
    public ResponseEntity<Users> createUsers(@Valid @RequestBody Users users) {
        return new ResponseEntity<>(userService.createUsers(users), HttpStatus.CREATED);
    }

//    @PostMapping("/create")
//    public UserResponse createUsers(@Valid @RequestBody UserRequest userRequest) {
//        return userService.createUsers();
//    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUsersById(@PathVariable Long id, @RequestBody Users users) {
        return new ResponseEntity<>(userService.updateUsersById(users), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUsersById(@PathVariable Long id) {
        userService.deleteUsersById(id);
    }
}
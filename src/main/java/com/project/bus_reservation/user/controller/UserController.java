package com.project.bus_reservation.user.controller;

import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.request.UserUpdateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})

public class UserController {
    @Autowired
    private UserService userService;

    @ApiResponse(responseCode = "201", description = "User created successfully")

    @PostMapping
    public ResponseEntity<Void> createUsers(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createUsers(userCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @ApiResponse(responseCode = "200", description = "Successfully retrieved user details")
    @GetMapping("/{firebaseUid}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String firebaseUid) {
        return new ResponseEntity<>(userService.getUsersById(firebaseUid), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "204", description = "User update successfully")
    @PatchMapping("/{firebaseUid}")
    public ResponseEntity<Void> updateUserByFirebaseUid(@PathVariable String firebaseUid, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUserByFirebaseUid(firebaseUid, userUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
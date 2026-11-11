package com.project.bus_reservation.user.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.request.UserUpdateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Management")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Resource conflict (e.g., duplicate phone number)",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user", description = "Registers a new user with name, email, and phone number")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping
    public ResponseEntity<Void> createUsers(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createUsers(userCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves details of a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user details",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true, example = "1")
            @PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUsersById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Update user", description = "Partially updates user details (name and/or email)")
    @ApiResponse(responseCode = "204", description = "User updated successfully")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserByUserId(
            @Parameter(description = "ID of the user to update", required = true, example = "1")
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUserByUserId(userId, userUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

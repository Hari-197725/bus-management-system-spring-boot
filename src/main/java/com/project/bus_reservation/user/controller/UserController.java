package com.project.bus_reservation.user.controller;

import com.project.bus_reservation.config.security.AdminGuard;
import com.project.bus_reservation.config.security.CurrentUser;
import com.project.bus_reservation.config.security.FirebaseUserDetails;
import com.project.bus_reservation.exception.custom.ForbiddenException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.user.dto.request.UserCreateRequest;
import com.project.bus_reservation.user.dto.request.UserUpdateRequest;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

@Tag(name = "User Management", description = "Operations for creating, retrieving, and updating users")
@RestController
@RequestMapping("/api/v1/user")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request - Invalid request body or parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Not Found - User does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Conflict - User already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Unexpected server issue", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminGuard adminGuard;

    @Operation(summary = "Create a new user", description = "Registers a new user profile using the Firebase authenticated identity")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping
    public ResponseEntity<Void> createUsers(
            @CurrentUser FirebaseUserDetails currentUser,
            @Valid @RequestBody UserCreateRequest userCreateRequest) {
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.createUsers(currentUser.getUid(), userCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users", description = "Admin only: Retrieves a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    @GetMapping

    public ResponseEntity<List<UserResponse>> getAllUsers(
            @CurrentUser FirebaseUserDetails currentUser) {
        adminGuard.requireAdmin(currentUser);
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves details of the currently authenticated user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @CurrentUser FirebaseUserDetails currentUser,
            @Parameter(description = "Unique identifier of the user") @PathVariable String userId) {
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!currentUser.getUid().equals(userId)) {
            throw new ForbiddenException(ErrorCode.ACCESS_DENIED,
                    "You are not allowed to access another user's profile");
        }
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Update user by ID", description = "Partially updates the authenticated user's own information")
    @ApiResponse(responseCode = "204", description = "User updated successfully")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(
            @CurrentUser FirebaseUserDetails currentUser,
            @Parameter(description = "Unique identifier of the user to update") @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.updateUserById(currentUser.getUid(), userId, userUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
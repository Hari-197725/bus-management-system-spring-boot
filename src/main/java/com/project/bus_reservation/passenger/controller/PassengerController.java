package com.project.bus_reservation.passenger.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.service.PassengerService;
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

@Tag(name = "Passenger Management", description = "Operations for registering and retrieving passengers for a user")
@RestController
@RequestMapping("api/v1/user/{userId}/passenger")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request - Invalid request body or parameters",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found - User not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Conflict - Passenger already registered",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error - Unexpected server issue",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public class PassengerController {
    @Autowired
    PassengerService passengerService;

    @Operation(summary = "Create a new passenger", description = "Registers a new passenger record associated with the specified user")
    @ApiResponse(responseCode = "201", description = "Passenger created successfully")
    @PostMapping
    public ResponseEntity<Void> createPassenger(
            @Parameter(description = "Unique identifier of the user")
            @PathVariable String userId,
            @Valid @RequestBody PassengerCreateRequest passengerCreateRequest) {
        passengerService.createPassenger(userId, passengerCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all passengers for a user", description = "Retrieves all passengers associated with the specified user")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all passengers",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PassengerResponse.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengerByUserId(
            @Parameter(description = "Unique identifier of the user")
            @PathVariable String userId) {
        return new ResponseEntity<>(passengerService.getAllPassengerByUserId(userId), HttpStatus.OK);
    }
}


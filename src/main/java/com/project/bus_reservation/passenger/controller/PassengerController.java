package com.project.bus_reservation.passenger.controller;

import com.project.bus_reservation.exception.response.ErrorResponse;
import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.service.PassengerService;
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
@RequestMapping("/api/v1/user/{userId}/passenger")
@Tag(name = "Passenger Management")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Resource conflict",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class PassengerController {
    @Autowired
    PassengerService passengerService;

    @Operation(summary = "Create a new passenger", description = "Adds a new passenger under the specified user account")
    @ApiResponse(responseCode = "201", description = "Passenger created successfully")
    @PostMapping
    public ResponseEntity<Void> createPassenger(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @Valid @RequestBody PassengerCreateRequest passengerCreateRequest) {
        passengerService.createPassenger(userId, passengerCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all passengers for a user", description = "Retrieves all passengers registered under the specified user account")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all passengers",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PassengerResponse.class))))
    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengerByUserId(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId) {
        return new ResponseEntity<>(passengerService.getAllPassengerByUserId(userId), HttpStatus.OK);
    }
}

package com.project.bus_reservation.passenger.controller;

import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.service.PassengerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user/{userId}/passenger")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class PassengerController {
    @Autowired
    PassengerService passengerService;

    @ApiResponse(responseCode = "201", description = "Passenger created successfully")
    @PostMapping
    public ResponseEntity<Void> createPassenger(@PathVariable Long userId, @Valid @RequestBody PassengerCreateRequest passengerCreateRequest) {
        passengerService.createPassenger(userId, passengerCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all passengers")
    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengerByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(passengerService.getAllPassengerByUserId(userId), HttpStatus.OK);
    }
}

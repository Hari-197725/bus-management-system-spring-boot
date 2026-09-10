package com.project.bus_reservation.passenger.controller;

import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user/{userId}/passenger")
public class PassengerController {
    @Autowired
    PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Void> createPassenger(@PathVariable Long userId, @Valid @RequestBody PassengerCreateRequest passengerCreateRequest) {
        passengerService.createPassenger(userId, passengerCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengerByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(passengerService.getAllPassengerByUserId(userId), HttpStatus.OK);
    }
}

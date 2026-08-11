package com.project.bus_reservation.seats.controller;

import com.project.bus_reservation.seats.dto.request.SeatRequest;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.service.SeatsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatsController {

    @Autowired
    private SeatsService seatsService;

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@Valid @RequestBody SeatRequest seatRequest) {
        return new ResponseEntity<>(seatsService.createSeat(seatRequest), HttpStatus.CREATED);
    }


}

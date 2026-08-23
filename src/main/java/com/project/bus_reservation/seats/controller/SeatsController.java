package com.project.bus_reservation.seats.controller;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.seats.service.SeatsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatsController {

    @Autowired
    private SeatsService seatsService;

    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getAllSeats() {
        return new ResponseEntity<>(seatsService.getAllSeats(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createSeat(@Valid @RequestBody BusRequest busSeatRequest) throws Exception {
        try {
            seatsService.createSeat(busSeatRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }


}

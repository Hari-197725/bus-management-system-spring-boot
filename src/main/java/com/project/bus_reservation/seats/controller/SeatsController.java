package com.project.bus_reservation.seats.controller;

import com.project.bus_reservation.buses.dto.response.BusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class SeatsController {

    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>>
}

package com.project.bus_reservation.busseats.controller;

import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.busseats.service.BusSeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/buses/{busId}")
public class BusSeatsController {

    @Autowired
    BusSeatsService busSeatsService;

    @GetMapping("/seats")
    public ResponseEntity<BusResponse.SeatResponse> getSeatsByBusId(@PathVariable Long id) {
        return new ResponseEntity(busSeatsService.getSeatsByBusId(id), HttpStatus.OK);
    }
}
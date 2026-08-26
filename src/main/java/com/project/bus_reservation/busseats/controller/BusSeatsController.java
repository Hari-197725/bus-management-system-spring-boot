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

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses/{busId}/seats")
public class BusSeatsController {

    @Autowired
    BusSeatsService busSeatsService;

    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getSeatsByBusId(@PathVariable Long busId) {
        return new ResponseEntity<>(busSeatsService.getSeatsByBusId(busId), HttpStatus.OK);
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<BusResponse.SeatResponse> getSeatBySeatId(@PathVariable Long busId, @PathVariable Long seatId) {
        return new ResponseEntity<>(busSeatsService.getSeatBySeatId(busId, seatId), HttpStatus.OK);
    }
}
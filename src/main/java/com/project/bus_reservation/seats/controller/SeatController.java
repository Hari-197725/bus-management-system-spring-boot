package com.project.bus_reservation.seats.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.seats.service.SeatService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seat")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class SeatController {

    @Autowired
    SeatService seatService;

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all seats")
    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getAllSeats() {
        return new ResponseEntity<>(seatService.getAllSeatsFromAllBus(), HttpStatus.OK);
    }
}


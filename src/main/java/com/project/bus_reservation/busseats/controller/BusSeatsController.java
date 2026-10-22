package com.project.bus_reservation.busseats.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.busseats.service.BusSeatsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class BusSeatsController {

    @Autowired
    BusSeatsService busSeatsService;

    @ApiResponse(responseCode = "200", description = "Successfully retrieved seats for the bus")
    @GetMapping
    public ResponseEntity<List<BusResponse.SeatResponse>> getSeatsByBusId(@PathVariable Long busId) {
        return new ResponseEntity<>(busSeatsService.getSeatsByBusId(busId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved seat details")
    @GetMapping("/{seatId}")
    public ResponseEntity<BusResponse.SeatResponse> getSeatBySeatId(@PathVariable Long busId, @PathVariable Long seatId) {
        return new ResponseEntity<>(busSeatsService.getSeatBySeatId(busId, seatId), HttpStatus.OK);
    }
}
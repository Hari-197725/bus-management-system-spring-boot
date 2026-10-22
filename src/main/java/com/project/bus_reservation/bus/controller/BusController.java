package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusService;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/bus")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class BusController {
    @Autowired
    BusService busService;

    @ApiResponse(responseCode = "201", description = "Bus created successfully")
    @PostMapping
    public ResponseEntity<Void> createBus(@PathVariable Long operatorId, @Valid @RequestBody BusCreateRequest busCreateRequest) {
        busService.createBus(operatorId, busCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all buses")
    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses(@PathVariable Long operatorId) {
        return new ResponseEntity<>(busService.getAllBuses(operatorId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved bus details")
    @GetMapping("/{busId}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long operatorId, @PathVariable Long busId) {
        return new ResponseEntity<>(busService.getBusById(operatorId, busId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved route for the bus")
    @GetMapping("/{busId}/route")
    public ResponseEntity<RouteResponse> getRouteByBusId(@PathVariable Long operatorId, @PathVariable Long busId) {
        return new ResponseEntity<>(busService.getRouteByBusId(operatorId, busId), HttpStatus.OK);
    }
}
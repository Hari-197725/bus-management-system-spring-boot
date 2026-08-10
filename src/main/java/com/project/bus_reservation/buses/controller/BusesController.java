package com.project.bus_reservation.buses.controller;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.service.BusesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
public class BusesController {

    @Autowired
    private BusesService busesService;

    @PostMapping
    public ResponseEntity<BusResponse> createBus(@Valid @RequestBody BusRequest busrequest) {
        return new ResponseEntity<>(busesService.createBus(busrequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses() {
        return new ResponseEntity<>(busesService.getAllBuses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long id) {
        return new ResponseEntity<>(busesService.getBusById(id), HttpStatus.OK);
    }
}

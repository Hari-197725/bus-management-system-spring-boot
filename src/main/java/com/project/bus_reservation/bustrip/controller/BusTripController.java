package com.project.bus_reservation.bustrip.controller;

import com.project.bus_reservation.bustrip.dto.request.BusTripCreateRequest;
import com.project.bus_reservation.bustrip.service.BusTripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/operator/{operatorId}/bustrip")
public class BusTripController {
    @Autowired
    BusTripService busTripService;

    @PostMapping
    public ResponseEntity<Void> createBusTrip(@PathVariable Long operatorId, @Valid @RequestBody BusTripCreateRequest busTripCreateRequest) {
        busTripService.createBusTrip(operatorId, busTripCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}

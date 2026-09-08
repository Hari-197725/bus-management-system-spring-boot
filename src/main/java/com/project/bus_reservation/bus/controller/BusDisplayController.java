package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bus")
public class BusDisplayController {
    @Autowired
    BusDisplayService busDisplayService;

    @GetMapping
    public ResponseEntity<List<BusResponse>> getBusesFromTo(@RequestParam String from, @RequestParam String to){
        return new ResponseEntity<>(busDisplayService.getBusesFromTo(from, to), HttpStatus.OK);
    }

}

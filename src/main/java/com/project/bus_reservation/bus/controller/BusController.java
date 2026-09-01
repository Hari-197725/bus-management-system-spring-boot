package com.project.bus_reservation.bus.controller;

import com.project.bus_reservation.bus.dto.request.BusCreateRequest;
import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.service.BusService;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping
    public ResponseEntity<BusResponse> createBus(@PathVariable Long operatorId, @Valid @RequestBody BusCreateRequest busCreateRequest) {
        return new ResponseEntity<>(busService.createBus(operatorId, busCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses(@PathVariable Long operatorId) {
        return new ResponseEntity<>(busService.getAllBuses(operatorId), HttpStatus.OK);
    }

    @GetMapping("/{busId}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long operatorId, @PathVariable Long busId) {
        return new ResponseEntity<>(busService.getBusById(operatorId, busId), HttpStatus.OK);
    }

    @GetMapping("/{busId}/route")
    public ResponseEntity<RouteResponse> getRouteByBusId(@PathVariable Long operatorId, @PathVariable Long busId) {
        return new ResponseEntity<>(busService.getAllRoutesByBusId(operatorId, busId), HttpStatus.OK);
    }


//    @PatchMapping("/{busId}")
//    public ResponseEntity<Void> updateBusByBusId(@PathVariable Long operatorId, @PathVariable Long busId, @Valid @RequestBody BusTransferUpdateRequest busTransferUpdateRequest) {
//        busService.updateBusByBusId(operatorId, busId, busTransferUpd
//    }


    @DeleteMapping("/{busId}")
    public ResponseEntity<Void> deleteBusById(@PathVariable Long operatorId, @PathVariable Long busId) {
        busService.deleteBusById(operatorId, busId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
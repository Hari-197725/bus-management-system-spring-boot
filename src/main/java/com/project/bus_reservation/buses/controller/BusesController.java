package com.project.bus_reservation.buses.controller;

import com.project.bus_reservation.buses.dto.request.BusRequest;
import com.project.bus_reservation.buses.dto.request.BusUpdateRequest;
import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.service.BusesService;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/bus")
public class BusesController {

    @Autowired
    private BusesService busesService;

    @PostMapping
    public ResponseEntity<BusResponse> createBus(@Valid @RequestBody BusRequest busrequest, @PathVariable Long operatorId) {
        return new ResponseEntity<>(busesService.createBus(busrequest, operatorId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses(@PathVariable Long operatorId) {
        return new ResponseEntity<>(busesService.getAllBuses(operatorId), HttpStatus.OK);
    }

    @GetMapping("/{busId}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long operatorId, @PathVariable Long busId) {
        return new ResponseEntity<>(busesService.getBusById(operatorId, busId), HttpStatus.OK);
    }

    @GetMapping("/{busId}/route")
    public ResponseEntity<RouteResponse> getRoutesByBusId(@PathVariable Long operatorId, @PathVariable Long busId){
        return new ResponseEntity<>(busesService.getAllRoutesByBusId(operatorId, busId), HttpStatus.OK);
    }



    @PatchMapping("/{busId}")
    public ResponseEntity<Void> updateBusByBusId(@PathVariable Long operatorId, @PathVariable Long busId, @Valid @RequestBody BusUpdateRequest busUpdateRequest) {
        return new ResponseEntity<>(busesService.updateById(operatorId, busId, busUpdateRequest), HttpStatus.NO_CONTENT);
    }


//    @DeleteMapping("/{busId}")
//    public void deleteBusById(@PathVariable Long operatorId, @PathVariable Long busId) {
//        busesService.deleteBusById(operatorId, busId);
//    }
}
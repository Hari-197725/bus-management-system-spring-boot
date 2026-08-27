package com.project.bus_reservation.route.controller;

import com.project.bus_reservation.route.dto.request.RouteRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/route")
public class RouteController {

    @Autowired
   private RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@PathVariable Long operatorId, @Valid @RequestBody RouteRequest routeRequest){
    return new ResponseEntity<>(routeService.createRoute(operatorId, routeRequest), HttpStatus.CREATED);
    }
}

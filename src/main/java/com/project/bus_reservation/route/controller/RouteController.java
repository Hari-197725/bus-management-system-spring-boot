package com.project.bus_reservation.route.controller;

import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@PathVariable Long operatorId, @Valid @RequestBody RouteCreateRequest routeCreateRequest) {
        return new ResponseEntity<>(routeService.createRoute(operatorId, routeCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes(@PathVariable Long operatorId){
        return new ResponseEntity<>(routeService.getAllRoutes(operatorId), HttpStatus.OK);
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRouteById(@PathVariable Long operatorId, @PathVariable Long routeId) {
        routeService.deleteRouteById(operatorId, routeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

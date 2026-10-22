package com.project.bus_reservation.route.controller;

import com.project.bus_reservation.route.dto.request.RouteCreateRequest;
import com.project.bus_reservation.route.dto.response.RouteResponse;
import com.project.bus_reservation.route.service.RouteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/route")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class RouteController {
    @Autowired
    private RouteService routeService;

    @ApiResponse(responseCode = "201", description = "Route created successfully")
    @PostMapping
    public ResponseEntity<Void> createRoute(@PathVariable Long operatorId, @Valid @RequestBody RouteCreateRequest routeCreateRequest) {
        routeService.createRoute(operatorId, routeCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all routes")
    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes(@PathVariable Long operatorId) {
        return new ResponseEntity<>(routeService.getAllRoutes(operatorId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved route details")
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Long operatorId, @PathVariable Long routeId) {
        return new ResponseEntity<>(routeService.getRouteById(operatorId, routeId), HttpStatus.OK);
    }
}
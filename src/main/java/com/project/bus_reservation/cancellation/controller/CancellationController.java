package com.project.bus_reservation.cancellation.controller;

import com.project.bus_reservation.cancellation.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/{userId}/booking/{bookingId}")
public class CancellationController {
    @Autowired
    CancellationService cancellationService;

    @DeleteMapping
    public ResponseEntity<Void> cancelBookingByBookingId(@PathVariable Long userId, @PathVariable Long bookingId){
        cancellationService.cancelBookingByBookingId(userId, bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

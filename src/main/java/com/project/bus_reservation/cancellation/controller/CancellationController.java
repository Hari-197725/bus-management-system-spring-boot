package com.project.bus_reservation.cancellation.controller;

import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.cancellation.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/{userId}/booking/{bookingId}/cancel")
public class CancellationController {
    @Autowired
    CancellationService cancellationService;

    @DeleteMapping
    public ResponseEntity<Void> cancelBookingByBookingId(@PathVariable Long userId, @PathVariable Long bookingId, @RequestBody CancellationRequest cancellationRequest){
        cancellationService.cancelBookingByBookingId(userId, bookingId, cancellationRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

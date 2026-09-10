package com.project.bus_reservation.booking.controller;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/{userId}/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<Void> createBooking(@PathVariable Long userId, @Valid @RequestBody BookingCreateRequest bookingCreateRequest) {
         bookingService.createBooking(userId, bookingCreateRequest);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

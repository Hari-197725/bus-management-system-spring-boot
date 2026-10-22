package com.project.bus_reservation.booking.controller;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.service.BookingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{userId}/booking")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "409"),
        @ApiResponse(responseCode = "500")
})
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @PostMapping
    public ResponseEntity<Void> createBooking(@PathVariable Long userId, @Valid @RequestBody BookingCreateRequest bookingCreateRequest) {
        bookingService.createBooking(userId, bookingCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved all bookings")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getAllBookings(userId), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successfully retrieved booking details")
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingByBookingId(@PathVariable Long userId, @PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBookingByBookingId(userId, bookingId), HttpStatus.OK);
    }

}

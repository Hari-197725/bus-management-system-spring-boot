package com.project.bus_reservation.cancellation.service;

import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CancellationService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    public void cancelBookingByBookingId(String userId, String bookingId, CancellationRequest cancellationRequest) {
        Booking booking = bookingRepository.findById(bookingId).block();
        if (booking == null || booking.getUserId() == null || !userId.equals(booking.getUserId())) {
            throw new NotFoundException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found with id: " + bookingId);
        }

        if (cancellationRequest.getSeatNumbers() != null && booking.getBookingDetails() != null) {
            // Cancel specific seats
            for (Integer seatNo : cancellationRequest.getSeatNumbers()) {
                // Mark the seat as available in the seats collection
                List<Seat> seats = seatRepository.findAll().collectList().block();
                if (seats != null) {
                    for (Seat seat : seats) {
                        if (seatNo.equals(seat.getSeatNumber())) {
                            seat.markAsAvailable();
                            seatRepository.save(seat).block();
                            break;
                        }
                    }
                }

                // Remove the booking detail entry for this seat
                Iterator<Booking.EmbeddedBookingDetail> iterator = booking.getBookingDetails().iterator();
                while (iterator.hasNext()) {
                    Booking.EmbeddedBookingDetail detail = iterator.next();
                    if (seatNo.equals(detail.getSeatNumber())) {
                        iterator.remove();
                        break;
                    }
                }
            }

            // If all details removed, delete the booking; otherwise update it
            if (booking.getBookingDetails().isEmpty()) {
                bookingRepository.deleteById(bookingId).block();
            } else {
                bookingRepository.save(booking).block();
            }
        } else {
            // Cancel entire booking — mark all seats as available
            if (booking.getBookingDetails() != null) {
                for (Booking.EmbeddedBookingDetail detail : booking.getBookingDetails()) {
                    List<Seat> seats = seatRepository.findAll().collectList().block();
                    if (seats != null) {
                        for (Seat seat : seats) {
                            if (detail.getSeatNumber() != null && detail.getSeatNumber().equals(seat.getSeatNumber())) {
                                seat.markAsAvailable();
                                seatRepository.save(seat).block();
                                break;
                            }
                        }
                    }
                }
            }
            bookingRepository.deleteById(bookingId).block();
        }
    }
}

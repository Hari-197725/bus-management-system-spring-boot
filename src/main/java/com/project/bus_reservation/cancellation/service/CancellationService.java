package com.project.bus_reservation.cancellation.service;

import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bookingdetail.repository.BookingDetailRepository;
import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancellationService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private SeatRepository seatRepository;

    public void cancelBookingByBookingId(String userId, String bookingId, CancellationRequest cancellationRequest) {
        Booking booking = bookingRepository.findById(bookingId).block();
        boolean matches = booking != null && (
                (booking.getUserId() != null && userId.equals(booking.getUserId()))
                || (booking.getUser() != null && userId.equals(booking.getUser().getId()))
        );
        if (!matches) {
            throw new NotFoundException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found with id: " + bookingId);
        }

        if (cancellationRequest.getSeatNumbers() != null) {
            List<BookingDetail> bookingDetailList = booking.getBookingDetails();
            if (bookingDetailList != null) {
                for (Integer seatNo : cancellationRequest.getSeatNumbers()) {
                    for (BookingDetail bookingDetail : bookingDetailList) {
                        if (bookingDetail.getSeat() != null && seatNo.equals(bookingDetail.getSeat().getSeatNumber())) {
                            Seat seat = bookingDetail.getSeat();
                            seat.markAsAvailable();
                            if (seat.getSeatId() != null) {
                                seat.setId(seat.getSeatId());
                            }
                            seatRepository.save(seat).block();
                            if (bookingDetail.getId() != null) {
                                bookingDetailRepository.deleteById(bookingDetail.getId()).block();
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            bookingDetailRepository.deleteById(bookingId).block();
        }

        bookingRepository.deleteById(bookingId).block();
    }
}
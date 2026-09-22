package com.project.bus_reservation.cancellation.service;

import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bookingdetail.repository.BookingDetailRepository;
import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class CancellationService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    @Transactional
    public void cancelBookingByBookingId(Long userId, Long bookingId, CancellationRequest cancellationRequest) {
        Booking booking = bookingRepository.findBookingByUserId(userId, bookingId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Booking not found with id: "));

        if (cancellationRequest.getSeatNumbers() != null) {
            List<BookingDetail> bookingDetailList = booking.getBookingDetails();
            for (Integer seatNo : cancellationRequest.getSeatNumbers()) {
                for (BookingDetail bookingDetail : bookingDetailList) {
                    if (bookingDetail.getSeat().getSeatNumber().equals(seatNo)) {
                        Seat seat = bookingDetail.getSeat();
                        seat.setSeatStatus(SeatStatus.AVAILABLE);
                        bookingDetailRepository.deleteById(bookingDetail.getId());
                        break;
                    }
                }
            }
        } else {
            bookingDetailRepository.deleteById(bookingId);
        }

        bookingRepository.deleteById(bookingId);
    }
}
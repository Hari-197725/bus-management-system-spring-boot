package com.project.bus_reservation.cancellation.service;

import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bookingdetail.repository.BookingDetailRepository;
import com.project.bus_reservation.cancellation.dto.request.CancellationRequest;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
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

    public void cancelBookingByBookingId(Long userId, Long bookingId, CancellationRequest cancellationRequest) {
        Booking booking = bookingRepository.findById(String.valueOf(bookingId)).block();
        if (booking == null || booking.getUser() == null || !userId.equals(booking.getUser().getNumericId())) {
            throw new NotFoundException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found with id: " + bookingId);
        }

        if (cancellationRequest.getSeatNumbers() != null) {
            List<BookingDetail> bookingDetailList = booking.getBookingDetails();
            if (bookingDetailList != null) {
                for (Integer seatNo : cancellationRequest.getSeatNumbers()) {
                    for (BookingDetail bookingDetail : bookingDetailList) {
                        if (bookingDetail.getSeat() != null && seatNo.equals(bookingDetail.getSeat().getSeatNumber())) {
                            Seat seat = bookingDetail.getSeat();
                            seat.setSeatStatus(SeatStatus.AVAILABLE);
                            seatRepository.save(seat).block();
                            if (bookingDetail.getId() != null) {
                                bookingDetailRepository.deleteById(String.valueOf(bookingDetail.getId())).block();
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            bookingDetailRepository.deleteById(String.valueOf(bookingId)).block();
        }

        bookingRepository.deleteById(String.valueOf(bookingId)).block();
    }
}
package com.project.bus_reservation.cancellation.service;

import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancellationService {
@Autowired
    BookingRepository bookingRepository;

public void cancelBookingByBookingId(Long userId, Long bookingId){


}
}

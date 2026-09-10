package com.project.bus_reservation.booking.mapper;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public static Booking toBookingEntity(User user, BusTrip busTrip, BookingCreateRequest bookingCreateRequest) {
        Booking booking = new Booking();
        booking.setTotalAmount(bookingCreateRequest.getTotalAmount());
        booking.setUser(user);
        booking.setBusTrip(busTrip);

        return booking;
    }

}
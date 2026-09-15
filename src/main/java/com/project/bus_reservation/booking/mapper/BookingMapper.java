package com.project.bus_reservation.booking.mapper;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public static Booking toBookingEntity(User user, BookingCreateRequest bookingCreateRequest) {
        Booking booking = new Booking();
        booking.setAmount(bookingCreateRequest.getAmount());
        booking.setUser(user);
        return booking;
    }

    public static BookingDetail toBookingDetails(Booking booking, Passenger passenger, Seat seat) {
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setBooking(booking);
        bookingDetail.setPassenger(passenger);
        bookingDetail.setSeat(seat);
        return bookingDetail;
    }

    public static BusTrip toBusTrip(Booking booking, Bus bus) {
        BusTrip busTrip = new BusTrip();
        busTrip.setBookings(booking);
        busTrip.setBus(bus);
        busTrip.setRoute(bus.getRoute());
        return busTrip;
    }
}
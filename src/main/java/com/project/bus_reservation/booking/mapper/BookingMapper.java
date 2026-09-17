package com.project.bus_reservation.booking.mapper;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public static Booking toBookingEntity(User user, BookingCreateRequest bookingCreateRequest) {
        Booking booking = new Booking();
        booking.setAmount(bookingCreateRequest.getAmount());
        booking.setUser(user);
        return booking;
    }

    public static BookingDetail toBookingDetailsEntity(Booking booking, Passenger passenger, Seat seat) {
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setBooking(booking);
        bookingDetail.setPassenger(passenger);
        bookingDetail.setSeat(seat);
        return bookingDetail;
    }

    public static BusTrip toBusTripEntity(Booking booking, Bus bus) {
        BusTrip busTrip = new BusTrip();
        busTrip.setBookings(booking);
        busTrip.setBus(bus);
        busTrip.setRoute(bus.getRoute());
        return busTrip;
    }

    public static BookingResponse.BusTripResponse toBusTripResponse(BusTrip busTrip) {
        return new BookingResponse.BusTripResponse(
                busTrip.getDepartureTime(),
                busTrip.getArrivalTime(),
                busTrip.getBus().getBusName(),
                busTrip.getBus().getBusType(),
                busTrip.getBus().getOperator().getOperatorName(),
                busTrip.getRoute().getSource(),
                busTrip.getRoute().getDestination(),
                busTrip.getRoute().getEstimatedDuration()
        );
    }

    public static BookingResponse.BookingDetailResponse toBookingDetailResponse(BookingDetail bookingDetail) {
        return new BookingResponse.BookingDetailResponse(bookingDetail.getPassenger().getAge(),
                bookingDetail.getPassenger().getGender(),
                bookingDetail.getPassenger().getName(),
                bookingDetail.getSeat().getSeatNumber());
    }

    public static BookingResponse toBookingResponse(Booking booking) {
        BookingResponse.BusTripResponse busTripResponse = toBusTripResponse(booking.getBusTrip());

        List<BookingResponse.BookingDetailResponse> bookingDetailResponseList = booking.getBookingDetails().stream()
                .map(bookingDetail -> toBookingDetailResponse(bookingDetail))
                .toList();

        return new BookingResponse(
                booking.getId(),
                booking.getBookingDate(),
                booking.getAmount(),
                booking.getUser().getName(),
                busTripResponse,
                bookingDetailResponseList);

    }
}
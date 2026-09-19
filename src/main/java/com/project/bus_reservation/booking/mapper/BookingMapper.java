package com.project.bus_reservation.booking.mapper;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.projection.BookingProjection;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.user.entity.User;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
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

    public static BookingResponse toBookingResponse(List<BookingProjection> bookingRows) {
        BookingProjection firstRow = bookingRows.get(0);

        BookingResponse.BusTripResponse busTripResponse = new BookingResponse.BusTripResponse(
                firstRow.getDepartureTime(),
                firstRow.getArrivalTime(),
                firstRow.getBusName(),
                firstRow.getBusType(),
                firstRow.getOperatorName(),
                firstRow.getSource(),
                firstRow.getDestination(),
                firstRow.getEstimatedDuration()
        );

        List<BookingResponse.BookingDetailResponse> details =
                bookingRows.stream()
                        .map(row -> new BookingResponse.BookingDetailResponse(
                                row.getPassengerAge(),
                                row.getPassengerGender(),
                                row.getPassengerName(),
                                row.getSeatNumber()
                        ))
                        .toList();

        return new BookingResponse(
                firstRow.getBookingId(),
                firstRow.getBookingDate(),
                firstRow.getAmount(),
                firstRow.getUserName(),
                busTripResponse,
                details
        );
    }


}
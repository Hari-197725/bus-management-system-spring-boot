package com.project.bus_reservation.booking.mapper;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.projection.BookingProjection;
import com.project.bus_reservation.booking.projection.BookingProjectionImpl;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.user.entity.User;
import com.google.cloud.Timestamp;
import com.project.bus_reservation.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BookingMapper {

    public static Booking toBookingEntity(User user, BookingCreateRequest bookingCreateRequest) {
        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());
        booking.setAmount(bookingCreateRequest.getAmount());
        booking.setUser(user);
        booking.setBookingDate(Timestamp.now());
        return booking;
    }

    public static BusTrip toBusTripEntity(Booking booking, Bus bus) {
        BusTrip busTrip = new BusTrip();
        busTrip.setId(UUID.randomUUID().toString());
        busTrip.setBookings(booking);
        busTrip.setBus(bus);
        busTrip.setRoute(bus.getRoute());
        busTrip.setDepartureTime(Timestamp.now());
        busTrip.setArrivalTime(Timestamp.ofTimeSecondsAndNanos(Timestamp.now().getSeconds() + 4 * 3600, 0));
        return busTrip;
    }

    public static BookingDetail toBookingDetailsEntity(Booking booking, Passenger passenger, Seat seat) {
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setId(UUID.randomUUID().toString());
        bookingDetail.setBooking(booking);
        bookingDetail.setPassenger(passenger);
        bookingDetail.setSeat(seat);
        return bookingDetail;
    }

    public static Booking buildCompleteBooking(Booking booking, BusTrip busTrip, List<BookingDetail> bookingDetails) {
        booking.setBusTrip(busTrip);
        booking.setBookingDetails(bookingDetails);
        return booking;
    }

    public static List<BookingProjection> toBookingProjections(Booking b) {
        List<BookingProjection> list = new ArrayList<>();
        BusTrip trip = b.getBusTrip();
        Bus bus = trip != null ? trip.getBus() : null;
        Route route = trip != null ? trip.getRoute() : (bus != null ? bus.getRoute() : null);

        if (b.getBookingDetails() != null && !b.getBookingDetails().isEmpty()) {
            for (BookingDetail detail : b.getBookingDetails()) {
                Passenger p = detail.getPassenger();
                Seat s = detail.getSeat();

                BookingProjectionImpl proj = BookingProjectionImpl.builder()
                        .bookingId(b.getId())
                        .bookingDate(DateTimeUtil.toLocalDate(b.getBookingDate()))
                        .amount(b.getAmount())
                        .userName(b.getUser() != null ? b.getUser().getName() : null)
                        .departureTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getDepartureTime() : null))
                        .arrivalTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getArrivalTime() : null))
                        .busName(bus != null ? bus.getBusName() : null)
                        .busType(bus != null ? bus.getBusType() : null)
                        .operatorName(bus != null && bus.getOperator() != null ? bus.getOperator().getOperatorName() : null)
                        .source(route != null ? route.getSource() : null)
                        .destination(route != null ? route.getDestination() : null)
                        .estimatedDuration(route != null ? route.getEstimatedDuration() : null)
                        .passengerAge(p != null ? p.getAge() : null)
                        .passengerGender(p != null ? p.getGender() : null)
                        .passengerName(p != null ? p.getName() : null)
                        .seatNumber(s != null ? s.getSeatNumber() : null)
                        .build();

                list.add(proj);
            }
        } else {
            BookingProjectionImpl proj = BookingProjectionImpl.builder()
                    .bookingId(b.getId())
                    .bookingDate(DateTimeUtil.toLocalDate(b.getBookingDate()))
                    .amount(b.getAmount())
                    .userName(b.getUser() != null ? b.getUser().getName() : null)
                    .departureTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getDepartureTime() : null))
                    .arrivalTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getArrivalTime() : null))
                    .busName(bus != null ? bus.getBusName() : null)
                    .busType(bus != null ? bus.getBusType() : null)
                    .operatorName(bus != null && bus.getOperator() != null ? bus.getOperator().getOperatorName() : null)
                    .source(route != null ? route.getSource() : null)
                    .destination(route != null ? route.getDestination() : null)
                    .estimatedDuration(route != null ? route.getEstimatedDuration() : null)
                    .build();
            list.add(proj);
        }
        return list;
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
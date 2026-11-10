package com.project.bus_reservation.booking.mapper;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.projection.BookingProjection;
import com.project.bus_reservation.booking.projection.BookingProjectionImpl;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.operator.entity.Operator;
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

    public static Booking toBookingEntity(User user, BookingCreateRequest bookingCreateRequest,
                                          Bus bus, Route route, String operatorName,
                                          List<Passenger> passengers, List<Seat> seats) {
        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());
        booking.setAmount(bookingCreateRequest.getAmount());
        booking.setUserId(user != null ? user.getId() : null);
        booking.setUserName(user != null ? user.getName() : null);
        booking.setBookingDate(Timestamp.now());

        // Build embedded bus trip with only the needed fields
        Booking.EmbeddedBusTrip embeddedBusTrip = new Booking.EmbeddedBusTrip();
        embeddedBusTrip.setDepartureTime(Timestamp.now());
        embeddedBusTrip.setArrivalTime(Timestamp.ofTimeSecondsAndNanos(Timestamp.now().getSeconds() + 4 * 3600, 0));
        embeddedBusTrip.setBusName(bus != null ? bus.getBusName() : null);
        embeddedBusTrip.setBusType(bus != null ? bus.getBusType() : null);
        embeddedBusTrip.setOperatorName(operatorName);
        embeddedBusTrip.setSource(route != null ? route.getSource() : null);
        embeddedBusTrip.setDestination(route != null ? route.getDestination() : null);
        embeddedBusTrip.setEstimatedDuration(route != null ? route.getEstimatedDuration() : null);
        booking.setBusTrip(embeddedBusTrip);

        // Build embedded booking details with only the needed fields
        List<Booking.EmbeddedBookingDetail> details = new ArrayList<>();
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            Seat seat = seats.get(i);
            Booking.EmbeddedBookingDetail detail = new Booking.EmbeddedBookingDetail();
            detail.setPassengerAge(passenger != null ? passenger.getAge() : null);
            detail.setPassengerGender(passenger != null ? passenger.getGender() : null);
            detail.setPassengerName(passenger != null ? passenger.getName() : null);
            detail.setSeatNumber(seat != null ? seat.getSeatNumber() : null);
            details.add(detail);
        }
        booking.setBookingDetails(details);

        return booking;
    }

    public static List<BookingProjection> toBookingProjections(Booking b) {
        return toBookingProjections(b, null);
    }

    public static List<BookingProjection> toBookingProjections(Booking b, String operatorName) {
        List<BookingProjection> list = new ArrayList<>();
        Booking.EmbeddedBusTrip trip = b.getBusTrip();

        // Use operatorName from the embedded trip if available, fall back to the parameter
        String opName = (trip != null && trip.getOperatorName() != null) ? trip.getOperatorName() : operatorName;

        if (b.getBookingDetails() != null && !b.getBookingDetails().isEmpty()) {
            for (Booking.EmbeddedBookingDetail detail : b.getBookingDetails()) {
                BookingProjectionImpl proj = BookingProjectionImpl.builder()
                        .bookingId(b.getId())
                        .bookingDate(DateTimeUtil.toLocalDate(b.getBookingDate()))
                        .amount(b.getAmount())
                        .userName(b.getUserName())
                        .departureTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getDepartureTime() : null))
                        .arrivalTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getArrivalTime() : null))
                        .busName(trip != null ? trip.getBusName() : null)
                        .busType(trip != null ? trip.getBusType() : null)
                        .operatorName(opName)
                        .source(trip != null ? trip.getSource() : null)
                        .destination(trip != null ? trip.getDestination() : null)
                        .estimatedDuration(trip != null ? trip.getEstimatedDuration() : null)
                        .passengerAge(detail.getPassengerAge())
                        .passengerGender(detail.getPassengerGender())
                        .passengerName(detail.getPassengerName())
                        .seatNumber(detail.getSeatNumber())
                        .build();

                list.add(proj);
            }
        } else {
            BookingProjectionImpl proj = BookingProjectionImpl.builder()
                    .bookingId(b.getId())
                    .bookingDate(DateTimeUtil.toLocalDate(b.getBookingDate()))
                    .amount(b.getAmount())
                    .userName(b.getUserName())
                    .departureTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getDepartureTime() : null))
                    .arrivalTime(DateTimeUtil.toLocalDateTime(trip != null ? trip.getArrivalTime() : null))
                    .busName(trip != null ? trip.getBusName() : null)
                    .busType(trip != null ? trip.getBusType() : null)
                    .operatorName(opName)
                    .source(trip != null ? trip.getSource() : null)
                    .destination(trip != null ? trip.getDestination() : null)
                    .estimatedDuration(trip != null ? trip.getEstimatedDuration() : null)
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

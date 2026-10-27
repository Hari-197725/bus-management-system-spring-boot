package com.project.bus_reservation.booking.service;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.mapper.BookingMapper;
import com.project.bus_reservation.booking.projection.BookingProjection;
import com.project.bus_reservation.booking.projection.BookingProjectionImpl;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.exception.custom.BadRequestException;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
import com.project.bus_reservation.route.entity.Route;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.repository.SeatRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private SeatRepository seatRepository;

    public void createBooking(Long userId, BookingCreateRequest bookingCreateRequest) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        List<BookingCreateRequest.passengerCreateRequest> passengerList = bookingCreateRequest.getPassengers();
        if (bookingCreateRequest.getSeatCount() != passengerList.size()) {
            throw new BadRequestException(ErrorCode.SEAT_PASSENGER_COUNT_MISMATCH, "Seat count " + bookingCreateRequest.getSeatCount() +
                    " does not match passenger count " + passengerList.size());
        }

        Bus bus = busRepository.findById(String.valueOf(bookingCreateRequest.getBusId())).block();
        if (bus == null || bus.getRoute() == null || !bookingCreateRequest.getRouteId().equals(bus.getRoute().getNumericId())) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus with id " + bookingCreateRequest.getBusId() + " not found for route with id "
                    + bookingCreateRequest.getRouteId());
        }

        Booking booking = BookingMapper.toBookingEntity(user, bookingCreateRequest);
        if (booking.getId() == null) {
            booking.setId(String.valueOf(System.currentTimeMillis()));
        }

        BusTrip busTrip = BookingMapper.toBusTripEntity(booking, bus);
        if (busTrip.getId() == null) {
            busTrip.setId(String.valueOf(System.currentTimeMillis()));
        }

        List<BookingDetail> bookingDetails = new ArrayList<>();

        for (BookingCreateRequest.passengerCreateRequest passengerReq : passengerList) {
            Passenger passenger = passengerRepository.findById(String.valueOf(passengerReq.getPassengerId())).block();
            if (passenger == null || passenger.getUser() == null || !userId.equals(passenger.getUser().getNumericId())) {
                throw new NotFoundException(ErrorCode.PASSENGER_NOT_FOUND, "Passenger with id " + passengerReq.getPassengerId() +
                        " not found for user id " + user);
            }

            Seat seat = null;
            if (bus.getSeats() != null) {
                for (Seat s : bus.getSeats()) {
                    if (s.getNumericId() != null && s.getNumericId().equals(passengerReq.getSeatId())) {
                        seat = s;
                        break;
                    }
                }
            }
            if (seat == null) {
                seat = seatRepository.findById(String.valueOf(passengerReq.getSeatId())).block();
            }

            if (seat == null) {
                throw new NotFoundException(ErrorCode.SEAT_NOT_FOUND, "Seat with id " + passengerReq.getSeatId() +
                        " not found for bus id " + bus.getNumericId());
            }

            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new BadRequestException(ErrorCode.SEAT_ALREADY_BOOKED, "Seat with id " + passengerReq.getSeatId() + " is already booked");
            }

            seat.setSeatStatus(SeatStatus.BOOKED);
            seatRepository.save(seat).block();

            BookingDetail detail = BookingMapper.toBookingDetailsEntity(booking, passenger, seat);
            if (detail.getId() == null) {
                detail.setId(String.valueOf(System.currentTimeMillis()));
            }
            bookingDetails.add(detail);
        }

        booking.setBookingDetails(bookingDetails);
        booking.setBusTrip(busTrip);
        bookingRepository.save(booking).block();
    }

    public List<BookingResponse> getAllBookings(Long userId) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        List<Booking> allBookings = bookingRepository.findAll().collectList().block();
        List<BookingProjection> projections = new ArrayList<>();

        if (allBookings != null) {
            for (Booking b : allBookings) {
                if (b.getUser() != null && userId.equals(b.getUser().getNumericId())) {
                    projections.addAll(toProjections(b));
                }
            }
        }

        Map<Long, List<BookingProjection>> groupedBookings = projections.stream()
                .collect(Collectors.groupingBy(
                        BookingProjection::getBookingId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<BookingResponse> responseList = new ArrayList<>();
        for (List<BookingProjection> bookingRows : groupedBookings.values()) {
            responseList.add(BookingMapper.toBookingResponse(bookingRows));
        }

        return responseList;
    }

    public BookingResponse getBookingByBookingId(Long userId, Long bookingId) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        Booking booking = bookingRepository.findById(String.valueOf(bookingId)).block();
        if (booking == null || booking.getUser() == null || !userId.equals(booking.getUser().getNumericId())) {
            throw new NotFoundException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found with id: " + bookingId);
        }

        List<BookingProjection> projections = toProjections(booking);
        return BookingMapper.toBookingResponse(projections);
    }

    private List<BookingProjection> toProjections(Booking b) {
        List<BookingProjection> list = new ArrayList<>();
        BusTrip trip = b.getBusTrip();
        Bus bus = trip != null ? trip.getBus() : null;
        Route route = trip != null ? trip.getRoute() : (bus != null ? bus.getRoute() : null);

        if (b.getBookingDetails() != null && !b.getBookingDetails().isEmpty()) {
            for (BookingDetail detail : b.getBookingDetails()) {
                Passenger p = detail.getPassenger();
                Seat s = detail.getSeat();

                BookingProjectionImpl proj = BookingProjectionImpl.builder()
                        .bookingId(b.getNumericId())
                        .bookingDate(b.getBookingDate())
                        .amount(b.getAmount())
                        .userName(b.getUser() != null ? b.getUser().getName() : null)
                        .departureTime(trip != null ? trip.getDepartureTime() : null)
                        .arrivalTime(trip != null ? trip.getArrivalTime() : null)
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
                    .bookingId(b.getNumericId())
                    .bookingDate(b.getBookingDate())
                    .amount(b.getAmount())
                    .userName(b.getUser() != null ? b.getUser().getName() : null)
                    .departureTime(trip != null ? trip.getDepartureTime() : null)
                    .arrivalTime(trip != null ? trip.getArrivalTime() : null)
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
}
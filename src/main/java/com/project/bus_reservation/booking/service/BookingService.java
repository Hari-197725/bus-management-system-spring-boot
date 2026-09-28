package com.project.bus_reservation.booking.service;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.mapper.BookingMapper;
import com.project.bus_reservation.booking.projection.BookingProjection;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.exception.BadRequestException;
import com.project.bus_reservation.exception.NotFoundException;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.enums.SeatStatus;
import com.project.bus_reservation.seats.repository.SeatRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    BusRepository busRepository;

    @Autowired
    SeatRepository seatRepository;

    @Transactional
    public void createBooking(Long userId, BookingCreateRequest bookingCreateRequest) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found with id: " + userId));

        List<BookingCreateRequest.passengerCreateRequest> passengerList = bookingCreateRequest.getPassengers();
        if (bookingCreateRequest.getSeatCount() != passengerList.size()) {
            throw new BadRequestException("SEAT_PASSENGER_COUNT_MISMATCH", "Seat count " + bookingCreateRequest.getSeatCount() +
                    " does not match passenger count " + passengerList.size());
        }

        Optional<Bus> bus = busRepository.findBusByRouteId(bookingCreateRequest.getBusId(), bookingCreateRequest.getRouteId());
        if (bus.isEmpty()) {
            throw new NotFoundException("BUS_NOT_FOUND", "Bus with id " + bookingCreateRequest.getBusId() + " not found for route with id "
                    + bookingCreateRequest.getRouteId());
        }

        Bus _bus = bus.get();
        Booking booking = BookingMapper.toBookingEntity(user, bookingCreateRequest);
        BusTrip busTrip = BookingMapper.toBusTripEntity(booking, _bus);

        List<BookingDetail> bookingDetails = new ArrayList<>();

        for (BookingCreateRequest.passengerCreateRequest passengerReq : passengerList) {
            Passenger passenger = passengerRepository.findPassengerByUserId(userId, passengerReq.getPassengerId())
                    .orElseThrow(() -> new NotFoundException("PASSENGER_NOT_FOUND", "Passenger with id " + passengerReq.getPassengerId() +
                            " not found for user id " + user));

            Seat seat = seatRepository.findBySeatAndBusId(passengerReq.getSeatId(), _bus.getId())
                    .orElseThrow(() -> new NotFoundException("SEAT_NOT_FOUND", "Seat with id " + passengerReq.getSeatId() +
                            " not found for bus id " + _bus.getId()));

            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new BadRequestException("SEAT_ALREADY_BOOKED", "Seat with id " + passengerReq.getSeatId() + " is already booked");
            }

            seat.setSeatStatus(SeatStatus.BOOKED);
            bookingDetails.add(BookingMapper.toBookingDetailsEntity(booking, passenger, seat));
        }

        booking.setBookingDetails(bookingDetails);
        booking.setBusTrip(busTrip);
        bookingRepository.save(booking);
    }

    public List<BookingResponse> getAllBookings(Long userId) {
        usersRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("USER_NOT_FOUND", "User not found with id: " + userId));

        List<BookingProjection> projections = bookingRepository.findBookingsByUserId(userId);

        Map<Long, List<BookingProjection>> groupedBookings = projections.stream()
                .collect(Collectors.groupingBy(
                        BookingProjection::getBookingId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<BookingResponse> responseList = new ArrayList<>();

        for (List<BookingProjection> bookingRows : groupedBookings.values()) {
            BookingProjection firstRow = bookingRows.get(0);
            responseList.add(BookingMapper.toBookingResponse(bookingRows));
        }

        return responseList;
    }

    public BookingResponse getBookingByBookingId(Long userId, Long bookingId) {
        usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found with id: " + userId));
        List<BookingProjection> projections = bookingRepository.findBookingByBookingId(userId, bookingId);

        return BookingMapper.toBookingResponse(projections);
    }
}
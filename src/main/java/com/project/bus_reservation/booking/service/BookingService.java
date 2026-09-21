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
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));

        List<BookingCreateRequest.passengerCreateRequest> passengerList = bookingCreateRequest.getPassengers();
        if (bookingCreateRequest.getSeatCount() != passengerList.size()) {
            throw new ResponseStatusException(BAD_REQUEST, "Seat count is mismatched with passenger");
        }

        Optional<Bus> bus = busRepository.findBusByRouteId(bookingCreateRequest.getBusId(), bookingCreateRequest.getRouteId());
        if (bus.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "There is no bus in this id: " + bookingCreateRequest.getBusId());
        }

        Bus _bus = bus.get();
        Booking booking = BookingMapper.toBookingEntity(user, bookingCreateRequest);
        BusTrip busTrip = BookingMapper.toBusTripEntity(booking, _bus);

        List<BookingDetail> bookingDetails = new ArrayList<>();

        for (BookingCreateRequest.passengerCreateRequest passengerReq : passengerList) {
            Passenger passenger = passengerRepository.findPassengerByUserId(userId, passengerReq.getPassengerId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Passenger not found with id: " + passengerReq.getPassengerId()));

            Seat seat = seatRepository.findBySeatAndBusId(passengerReq.getSeatId(), _bus.getId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Seat not found with id: " + passengerReq.getSeatId()));

            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new ResponseStatusException(BAD_REQUEST, "Seat already booked" + passengerReq.getSeatId());
            }

            seat.setSeatStatus(SeatStatus.BOOKED);

            BookingDetail bookingDetail = BookingMapper.toBookingDetailsEntity(booking, passenger, seat);
            bookingDetails.add(bookingDetail);
        }

        booking.setBookingDetails(bookingDetails);
        booking.setBusTrip(busTrip);
        bookingRepository.save(booking);
    }

    public List<BookingResponse> getAllBookings(Long userId) {
        usersRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));

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
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));
        List<BookingProjection> projections = bookingRepository.findBookingByBookingId(userId, bookingId);

        return BookingMapper.toBookingResponse(projections);
    }
}
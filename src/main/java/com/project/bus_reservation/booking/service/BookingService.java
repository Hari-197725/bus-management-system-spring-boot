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
import com.project.bus_reservation.exception.custom.BadRequestException;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
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

    public void createBooking(String userId, BookingCreateRequest bookingCreateRequest) {
        User user = usersRepository.findById(userId).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        List<BookingCreateRequest.passengerCreateRequest> passengerList = bookingCreateRequest.getPassengers();
        if (bookingCreateRequest.getSeatCount() != passengerList.size()) {
            throw new BadRequestException(ErrorCode.SEAT_PASSENGER_COUNT_MISMATCH, "Seat count " + bookingCreateRequest.getSeatCount() +
                    " does not match passenger count " + passengerList.size());
        }

        Bus bus = busRepository.findById(bookingCreateRequest.getBusId()).block();
        if (bus == null || bus.getRoute() == null || !bookingCreateRequest.getRouteId().equals(bus.getRoute().getId())) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus with id " + bookingCreateRequest.getBusId() + " not found for route with id "
                    + bookingCreateRequest.getRouteId());
        }

        Booking booking = BookingMapper.toBookingEntity(user, bookingCreateRequest);
        BusTrip busTrip = BookingMapper.toBusTripEntity(booking, bus);

        List<BookingDetail> bookingDetails = new ArrayList<>();

        for (BookingCreateRequest.passengerCreateRequest passengerReq : passengerList) {
            Passenger passenger = passengerRepository.findById(passengerReq.getPassengerId()).block();
            if (passenger == null || passenger.getUserId() == null || !userId.equals(passenger.getUserId())) {
                throw new NotFoundException(ErrorCode.PASSENGER_NOT_FOUND, "Passenger with id " + passengerReq.getPassengerId() +
                        " not found for user " + userId);
            }

            Seat seat = null;
            if (bus.getSeats() != null) {
                for (Seat s : bus.getSeats()) {
                    if (s.getId() != null && s.getId().equals(passengerReq.getSeatId())) {
                        seat = s;
                        break;
                    }
                }
            }
            if (seat == null) {
                seat = seatRepository.findById(passengerReq.getSeatId()).block();
            }

            if (seat == null) {
                throw new NotFoundException(ErrorCode.SEAT_NOT_FOUND, "Seat with id " + passengerReq.getSeatId() +
                        " not found for bus id " + bus.getId());
            }

            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new BadRequestException(ErrorCode.SEAT_ALREADY_BOOKED, "Seat with id " + passengerReq.getSeatId() + " is already booked");
            }

            seat.markAsBooked();
            seatRepository.save(seat).block();

            BookingDetail detail = BookingMapper.toBookingDetailsEntity(booking, passenger, seat);
            bookingDetails.add(detail);
        }

        BookingMapper.buildCompleteBooking(booking, busTrip, bookingDetails);
        bookingRepository.save(booking).block();
    }

    public List<BookingResponse> getAllBookings(String userId) {
        User user = usersRepository.findById(userId).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        List<Booking> allBookings = bookingRepository.findAll().collectList().block();
        List<BookingProjection> projections = new ArrayList<>();

        if (allBookings != null) {
            for (Booking b : allBookings) {
                if (b.getUser() != null && userId.equals(b.getUser().getId())) {
                    projections.addAll(BookingMapper.toBookingProjections(b));
                }
            }
        }

        Map<String, List<BookingProjection>> groupedBookings = projections.stream()
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

    public BookingResponse getBookingByBookingId(String userId, String bookingId) {
        User user = usersRepository.findById(userId).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        Booking booking = bookingRepository.findById(bookingId).block();
        if (booking == null || booking.getUser() == null || !userId.equals(booking.getUser().getId())) {
            throw new NotFoundException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found with id: " + bookingId);
        }

        List<BookingProjection> projections = BookingMapper.toBookingProjections(booking);
        return BookingMapper.toBookingResponse(projections);
    }
}
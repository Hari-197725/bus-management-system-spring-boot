package com.project.bus_reservation.booking.service;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.dto.response.BookingResponse;
import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.mapper.BookingMapper;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.repository.SeatRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

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

        Optional<Bus> bus = busRepository.checkBusAndRouteExist(bookingCreateRequest.getBusId(), bookingCreateRequest.getRouteId());
        if (bus.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "There is no bus in this id: " + bookingCreateRequest.getBusId());
        }

        Bus _bus = bus.get();
        Booking booking = BookingMapper.toBookingEntity(user, bookingCreateRequest);
        BusTrip busTrip = BookingMapper.toBusTripEntity(booking, _bus);

        List<BookingDetail> bookingDetails = new ArrayList<>();
        for (BookingCreateRequest.passengerCreateRequest passengerReq : passengerList) {
            Optional<Passenger> passenger = passengerRepository.findByUserAndPassenger(userId, passengerReq.getPassengerId());
            Optional<Seat> seat = seatRepository.findBySeatAndBus(passengerReq.getSeatId(), _bus.getId());

            if (passenger.isEmpty() || seat.isEmpty()) {
                throw new ResponseStatusException(NOT_FOUND, "Passenger or Seat not found");
            }

            BookingDetail bookingDetail = BookingMapper.toBookingDetailsEntity(booking, passenger.get(), seat.get());
            bookingDetails.add(bookingDetail);
        }

        booking.setBookingDetails(bookingDetails);
        booking.setBusTrip(busTrip);
        bookingRepository.save(booking);
    }

    public List<BookingResponse> getAllBookings(Long userId){
        User user = usersRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));

        List<Booking> bookingList  = user.getBookings();
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for(Booking booking : bookingList){
            bookingResponseList.add(BookingMapper.toBookingResponse(booking));
        }

        return bookingResponseList;
    }




}
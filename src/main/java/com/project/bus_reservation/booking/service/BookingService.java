package com.project.bus_reservation.booking.service;

import com.project.bus_reservation.booking.dto.request.BookingCreateRequest;
import com.project.bus_reservation.booking.mapper.BookingMapper;
import com.project.bus_reservation.booking.repository.BookingRepository;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.bustrip.repository.BusTripRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BusTripRepository busTripRepository;


    public void createBooking(Long userId, BookingCreateRequest bookingCreateRequest) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));

        Optional<BusTrip> busTrip = busTripRepository.findById(bookingCreateRequest.getBusTripId());

        BusTrip _busTrip = null;
        if (busTrip.isPresent()) {
            _busTrip = busTrip.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Bus trip now found with id: " + bookingCreateRequest.getBusTripId());
        }

        bookingRepository.save(BookingMapper.toBookingEntity(user, _busTrip, bookingCreateRequest));

    }

}

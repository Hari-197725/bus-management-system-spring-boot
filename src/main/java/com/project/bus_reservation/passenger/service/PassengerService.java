package com.project.bus_reservation.passenger.service;

import com.project.bus_reservation.exception.NotFoundException;
import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.mapper.PassengerMapper;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UserPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {
    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    UserPostgresRepository userPostgresRepository;

    public void createPassenger(Long userId, PassengerCreateRequest passengerCreateRequest) {
        User user = userPostgresRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found with id; " + userId));

        passengerRepository.save(PassengerMapper.toPassengerEntity(user, passengerCreateRequest));
    }

    public List<PassengerResponse> getAllPassengerByUserId(Long userId) {
        User user = userPostgresRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found with id: " + userId));

        List<Passenger> passengerList = user.getPassengers();
        List<PassengerResponse> passengerResponseList = new ArrayList<>();

        for (Passenger passenger : passengerList) {
            passengerResponseList.add(PassengerMapper.toPassengerResponse(user, passenger));
        }

        return passengerResponseList;
    }

}
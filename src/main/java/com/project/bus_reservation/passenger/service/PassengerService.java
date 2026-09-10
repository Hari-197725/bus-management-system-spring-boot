package com.project.bus_reservation.passenger.service;

import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.mapper.PassengerMapper;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class PassengerService {
    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    UsersRepository usersRepository;

    public void createPassenger(Long userId, PassengerCreateRequest passengerCreateRequest) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id; " + userId));

        passengerRepository.save(PassengerMapper.toPassengerEntity(user, passengerCreateRequest));
    }

    public List<PassengerResponse> getAllPassengerByUserId(Long userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found with id: " + userId));

        List<Passenger> passengerList = user.getPassengers();
        List<PassengerResponse> passengerResponseList = new ArrayList<>();

        for (Passenger passenger : passengerList) {
            passengerResponseList.add(PassengerMapper.toPassengerResponse(user, passenger));
        }

        return passengerResponseList;

    }

}

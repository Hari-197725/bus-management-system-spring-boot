package com.project.bus_reservation.passenger.service;

import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.passenger.mapper.PassengerMapper;
import com.project.bus_reservation.passenger.repository.PassengerRepository;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private UsersRepository usersRepository;

    public void createPassenger(Long userId, PassengerCreateRequest passengerCreateRequest) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        Passenger passenger = PassengerMapper.toPassengerEntity(user, passengerCreateRequest);
        if (passenger.getId() == null) {
            passenger.setId(String.valueOf(System.currentTimeMillis()));
        }
        passengerRepository.save(passenger).block();

        if (user.getPassengers() == null) {
            user.setPassengers(new ArrayList<>());
        }
        user.getPassengers().add(passenger);
        usersRepository.save(user).block();
    }

    public List<PassengerResponse> getAllPassengerByUserId(Long userId) {
        User user = usersRepository.findById(String.valueOf(userId)).block();
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
        }

        List<Passenger> passengerList = user.getPassengers();
        List<PassengerResponse> passengerResponseList = new ArrayList<>();

        if (passengerList != null) {
            for (Passenger passenger : passengerList) {
                passengerResponseList.add(PassengerMapper.toPassengerResponse(user, passenger));
            }
        }

        return passengerResponseList;
    }
}
package com.project.bus_reservation.passenger.mapper;

import com.project.bus_reservation.passenger.dto.request.PassengerCreateRequest;
import com.project.bus_reservation.passenger.dto.response.PassengerResponse;
import com.project.bus_reservation.passenger.entity.Passenger;
import com.project.bus_reservation.user.dto.response.UserResponse;
import com.project.bus_reservation.user.entity.User;
import com.project.bus_reservation.user.mapper.UserMapper;
import org.apache.tomcat.websocket.server.WsWriteTimeout;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public static Passenger toPassengerEntity(User user, PassengerCreateRequest passengerCreateRequest) {
        Passenger passenger = new Passenger();
        passenger.setAge(passengerCreateRequest.getAge());
        passenger.setGender(passengerCreateRequest.getGender());
        passenger.setName(passengerCreateRequest.getName());
        passenger.setUser(user);

        return passenger;
    }

    public static PassengerResponse toPassengerResponse(User user, Passenger passenger) {
        UserResponse userResponse = UserMapper.toUserResponse(user);
        return new PassengerResponse(passenger.getId(), passenger.getAge(), passenger.getGender(), passenger.getName(), userResponse);
    }
}
package com.project.bus_reservation.seats.service;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    public List<BusResponse.SeatResponse> getAllSeatsFromAllBus() {
        List<Seat> seats = seatRepository.findAll();
        List<BusResponse.SeatResponse> seatResponses = new ArrayList<>();

        for (Seat seat : seats) {
            seatResponses.add(BusMapper.toSeatResponse(seat));
        }



        return seatResponses;
    }
}

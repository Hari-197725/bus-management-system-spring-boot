package com.project.bus_reservation.busseats.service;

import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.mapper.BusMapper;
import com.project.bus_reservation.buses.repository.BusesRepository;
import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusSeatsService {

    @Autowired
    BusesRepository busesRepository;

    public List<BusResponse.SeatResponse> getSeatsByBusId(Long busId) {
        Optional<Bus> bus = busesRepository.findById(busId);
        Bus _bus = null;
        if (bus.isPresent()) {
            _bus = bus.get();
        }


        List<Seat> seats = _bus.getSeats();
        for(Seat seat : seats){
            
        }

        List<BusResponse.SeatResponse> seatResponses = new ArrayList<>();
        for(Seat seat : seats){
          seatResponses.add(BusMapper.toResponseSeat(seat));
        }

        return seatResponses;

    }
}
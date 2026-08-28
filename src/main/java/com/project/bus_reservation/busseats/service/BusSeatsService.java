package com.project.bus_reservation.busseats.service;

import com.project.bus_reservation.buses.dto.response.BusResponse;
import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.mapper.BusMapper;
import com.project.bus_reservation.buses.repository.BusesRepository;
import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        List<BusResponse.SeatResponse> seatResponses = new ArrayList<>();
        for (Seat seat : seats) {
            seatResponses.add(BusMapper.toSeatResponse(seat));
        }

        return seatResponses;
    }

    public BusResponse.SeatResponse getSeatBySeatId(Long busId, Long seatId) {
        Bus bus = busesRepository.findById(busId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Bus not found with id: " + busId));

        List<Seat> seats = bus.getSeats();
        BusResponse.SeatResponse seatResponse = null;

        for (Seat seat : seats) {
            if (seat.getId().equals(seatId)) {
                seatResponse = BusMapper.toSeatResponse(seat);
                break;
            }
        }

        return seatResponse;
    }
}
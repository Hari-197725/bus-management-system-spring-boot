package com.project.bus_reservation.seats.service;

import com.project.bus_reservation.buses.entity.Bus;
import com.project.bus_reservation.buses.repository.BusesRepository;
import com.project.bus_reservation.seats.dto.request.BusSeatRequest;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.mapper.SeatMapper;
import com.project.bus_reservation.seats.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SeatsService {

    @Autowired
    private SeatsRepository seatsRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private BusesRepository busesRepository;

    public void createSeat(BusSeatRequest busSeatRequest) throws Exception {
        Bus bus = busesRepository.findById(busSeatRequest.getBusId())
                .orElseThrow(() ->
                        new ResponseStatusException(BAD_REQUEST, "Invalid BusId"));

        if (bus.getTotalSeats() - bus.getSeats().size() < busSeatRequest.getSeats().size()) {
            throw new ResponseStatusException(BAD_REQUEST, "Seat limit exceeded");
        }

        List<Seat> seats = seatMapper.toEntity(busSeatRequest);
        for (Seat seat : seats) {
            seat.setBus(bus);
        }

        seatsRepository.saveAll(seats);
    }

    public List<SeatResponse> getAllSeats() {
        return seatsRepository.findAll().stream().map(seatMapper::toResponse).toList();
    }

//    public SeatResponse getSeatById (Long id){
//
//    }
}

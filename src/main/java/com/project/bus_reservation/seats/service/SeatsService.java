package com.project.bus_reservation.seats.service;

import com.project.bus_reservation.seats.dto.request.SeatRequest;
import com.project.bus_reservation.seats.dto.response.SeatResponse;
import com.project.bus_reservation.seats.entity.Seat;
import com.project.bus_reservation.seats.mapper.SeatMapper;
import com.project.bus_reservation.seats.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {

    @Autowired
    private SeatsRepository seatsRepository;

    @Autowired
    private SeatMapper seatMapper;

    public SeatResponse createSeat(SeatRequest seatRequest) {
        Seat seat = seatMapper.toEntity(seatRequest);
        return seatMapper.toResponse(seatsRepository.save(seat));
    }

    public List<SeatResponse> getAllSeats() {
        return seatsRepository.findAll().stream().map(seatMapper::toResponse).toList();
    }

    public SeatResponse getSeatById (Long id){
        
    }
}

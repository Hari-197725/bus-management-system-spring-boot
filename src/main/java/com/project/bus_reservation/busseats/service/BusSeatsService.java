package com.project.bus_reservation.busseats.service;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusRepository;
import com.project.bus_reservation.exception.custom.NotFoundException;
import com.project.bus_reservation.exception.enums.ErrorCode;
import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusSeatsService {

    @Autowired
    private BusRepository busRepository;

    public List<BusResponse.SeatResponse> getSeatsByBusId(Long busId) {
        Bus bus = busRepository.findById(String.valueOf(busId)).block();
        if (bus == null) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus not found with id: " + busId);
        }

        List<BusResponse.SeatResponse> seatResponses = new ArrayList<>();
        List<Seat> seats = bus.getSeats();
        if (seats != null) {
            for (Seat seat : seats) {
                seatResponses.add(BusMapper.toSeatResponse(seat));
            }
        }

        return seatResponses;
    }

    public BusResponse.SeatResponse getSeatBySeatId(Long busId, Long seatId) {
        Bus bus = busRepository.findById(String.valueOf(busId)).block();
        if (bus == null) {
            throw new NotFoundException(ErrorCode.BUS_NOT_FOUND, "Bus not found with id: " + busId);
        }

        List<Seat> seats = bus.getSeats();
        BusResponse.SeatResponse seatResponse = null;

        if (seats != null) {
            for (Seat seat : seats) {
                if (seat.getNumericId() != null && seat.getNumericId().equals(seatId)) {
                    seatResponse = BusMapper.toSeatResponse(seat);
                    break;
                }
            }
        }

        return seatResponse;
    }
}
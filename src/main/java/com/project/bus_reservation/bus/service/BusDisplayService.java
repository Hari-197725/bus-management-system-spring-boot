package com.project.bus_reservation.bus.service;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class BusDisplayService {
    @Autowired
    BusRepository busRepository;

    public List<BusResponse> getBusesFromTo(String from, String to) {
        Optional<List<Bus>> busList = busRepository.findBySourceAndDestination(from, to);
        List<BusResponse> busResponseList = new ArrayList<>();

        if (busList.isPresent()) {
            List<Bus> buses = busList.get();
            for (Bus bus : buses) {
                busResponseList.add(BusMapper.toBusResponse(bus));
            }
        }

        return busResponseList;
    }
}
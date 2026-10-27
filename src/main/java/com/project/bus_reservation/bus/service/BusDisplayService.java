package com.project.bus_reservation.bus.service;

import com.project.bus_reservation.bus.dto.response.BusResponse;
import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bus.mapper.BusMapper;
import com.project.bus_reservation.bus.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusDisplayService {
    @Autowired
    private BusRepository busRepository;

    public List<BusResponse> getBusesFromTo(String from, String to) {
        List<Bus> allBuses = busRepository.findAll().collectList().block();
        List<BusResponse> busResponseList = new ArrayList<>();

        if (allBuses != null) {
            for (Bus bus : allBuses) {
                if (bus.getRoute() != null
                        && from.equalsIgnoreCase(bus.getRoute().getSource())
                        && to.equalsIgnoreCase(bus.getRoute().getDestination())) {
                    busResponseList.add(BusMapper.toBusResponse(bus));
                }
            }
        }

        return busResponseList;
    }
}
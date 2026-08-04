package com.project.bus_reservation.buses.service;

import com.project.bus_reservation.buses.repository.BusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusesService {

    @Autowired
    private BusesRepository busesRepository;


}

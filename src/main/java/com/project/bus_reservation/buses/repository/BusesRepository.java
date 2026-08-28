package com.project.bus_reservation.buses.repository;

import com.project.bus_reservation.buses.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusesRepository extends JpaRepository<Bus, Long> {
}


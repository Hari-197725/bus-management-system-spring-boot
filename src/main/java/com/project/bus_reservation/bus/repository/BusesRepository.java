package com.project.bus_reservation.bus.repository;

import com.project.bus_reservation.bus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusesRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findByIdAndOperatorId(Long id, Long operatorId);
}


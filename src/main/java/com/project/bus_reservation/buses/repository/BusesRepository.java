package com.project.bus_reservation.buses.repository;

import com.project.bus_reservation.buses.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusesRepository extends JpaRepository<Bus, Long> {
//    Optional<Bus> findByIdAndoperatorId(Long busId, Long operatorId);
}


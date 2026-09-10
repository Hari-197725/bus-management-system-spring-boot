package com.project.bus_reservation.bustrip.repository;

import com.project.bus_reservation.bustrip.entity.BusTrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusTripRepository extends JpaRepository<BusTrip, Long> {
}

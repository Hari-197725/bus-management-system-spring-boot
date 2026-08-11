package com.project.bus_reservation.seats.repository;

import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRepository extends JpaRepository<Seat, Long> {
}

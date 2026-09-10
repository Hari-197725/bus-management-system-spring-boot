package com.project.bus_reservation.passenger.repository;

import com.project.bus_reservation.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}

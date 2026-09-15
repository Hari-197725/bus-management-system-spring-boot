package com.project.bus_reservation.passenger.repository;

import com.project.bus_reservation.passenger.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query(value = "select passengers.* from passengers where passengers.user_id = :userId and passengers.id = :passengerId;", nativeQuery = true)
    Optional<Passenger> findByUserAndPassenger(@Param("userId") Long userId, @Param("passengerId") Long passengerId);
}
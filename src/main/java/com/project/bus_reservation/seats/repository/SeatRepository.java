package com.project.bus_reservation.seats.repository;

import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query(value ="select * from seats where id = :seatId and bus_id = :busId", nativeQuery = true)
    Optional<Seat> findBySeatAndBus(@Param("seatId") Long seatId, @Param("busId") Long busId);
}

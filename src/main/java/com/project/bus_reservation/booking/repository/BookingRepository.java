package com.project.bus_reservation.booking.repository;

import com.project.bus_reservation.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

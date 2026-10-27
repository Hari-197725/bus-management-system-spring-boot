package com.project.bus_reservation.seats.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.seats.entity.Seat;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends FirestoreReactiveRepository<Seat> {
}

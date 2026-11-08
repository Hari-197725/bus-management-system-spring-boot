package com.project.bus_reservation.booking.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.booking.entity.Booking;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface BookingRepository extends FirestoreReactiveRepository<Booking> {
    Flux<Booking> findByUserId(String userId);
}
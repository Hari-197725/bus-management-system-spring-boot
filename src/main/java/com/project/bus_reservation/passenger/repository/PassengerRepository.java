package com.project.bus_reservation.passenger.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.passenger.entity.Passenger;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PassengerRepository extends FirestoreReactiveRepository<Passenger> {
    Flux<Passenger> findByUserId(String userId);
}
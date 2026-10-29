package com.project.bus_reservation.bus.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.bus.entity.Bus;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends FirestoreReactiveRepository<Bus> {
}

package com.project.bus_reservation.bustrip.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import org.springframework.stereotype.Repository;

@Repository
public interface BusTripRepository extends FirestoreReactiveRepository<BusTrip> {
}

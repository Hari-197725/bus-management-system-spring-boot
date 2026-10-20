package com.project.bus_reservation.user.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFirebaseRepository extends FirestoreReactiveRepository<User> {
}

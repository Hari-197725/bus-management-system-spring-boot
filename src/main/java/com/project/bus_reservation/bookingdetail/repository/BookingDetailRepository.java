package com.project.bus_reservation.bookingdetail.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends FirestoreReactiveRepository<BookingDetail> {
}

package com.project.bus_reservation.bookingdetail.repository;

import com.project.bus_reservation.bookingdetail.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
}

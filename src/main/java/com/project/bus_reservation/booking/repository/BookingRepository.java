package com.project.bus_reservation.booking.repository;

import com.project.bus_reservation.booking.entity.Booking;
import com.project.bus_reservation.booking.projection.BookingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT bookings.id AS booking_id, bookings.booking_date AS booking_date, bookings.amount AS amount, users.name AS user_name, bus_trips.departure_time AS " +
            "departure_time, bus_trips.arrival_time AS arrival_time, buses.bus_name AS bus_name, buses.bus_type AS bus_type, operators.operator_name AS operator_name, " +
            "routes.source AS source, routes.destination AS destination, routes.estimated_duration AS estimated_duration, passengers.age AS passenger_age, " +
            "passengers.gender AS passenger_gender, passengers.name AS passenger_name, seats.seat_number AS seat_number FROM bookings INNER JOIN booking_details ON " +
            "booking_details.booking_id = bookings.id INNER JOIN bus_trips ON bus_trips.id = bookings.bus_trip_id INNER JOIN passengers ON passengers.id = " +
            "booking_details.passenger_id INNER JOIN seats ON seats.id = booking_details.seat_id INNER JOIN routes ON routes.id = bus_trips.route_id INNER JOIN buses " +
            "ON buses.id = bus_trips.bus_id INNER JOIN operators ON operators.id = buses.operator_id INNER JOIN users ON users.id = bookings.user_id WHERE users.id = :userId",
            nativeQuery = true)
    List<BookingProjection> findBookingsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT bookings.id AS booking_id, bookings.booking_date AS booking_date, bookings.amount AS amount, users.name AS user_name, bus_trips.departure_time AS " +
            "departure_time, bus_trips.arrival_time AS arrival_time, buses.bus_name AS bus_name, buses.bus_type AS bus_type, operators.operator_name AS operator_name, " +
            "routes.source AS source, routes.destination AS destination, routes.estimated_duration AS estimated_duration, passengers.age AS passenger_age, " +
            "passengers.gender AS passenger_gender, passengers.name AS passenger_name, seats.seat_number AS seat_number FROM bookings INNER JOIN booking_details ON " +
            "booking_details.booking_id = bookings.id INNER JOIN bus_trips ON bus_trips.id = bookings.bus_trip_id INNER JOIN passengers ON passengers.id = " +
            "booking_details.passenger_id INNER JOIN seats ON seats.id = booking_details.seat_id INNER JOIN routes ON routes.id = bus_trips.route_id INNER JOIN buses " +
            "ON buses.id = bus_trips.bus_id INNER JOIN operators ON operators.id = buses.operator_id INNER JOIN users ON users.id = bookings.user_id WHERE users.id = :userId " +
            "and bookings.id = :bookingId", nativeQuery = true)
    List<BookingProjection> findBookingByBookingId(@Param("userId") Long userId, @Param("bookingId") Long bookingId);

    @Query(value = "select * from bookings where user_id = :userId and id = :bookingId;", nativeQuery = true)
   Optional<Booking> findBookingByUserId(@Param("userId") Long userId, @Param("bookingId") Long bookingId);

}

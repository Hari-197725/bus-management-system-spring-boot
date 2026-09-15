package com.project.bus_reservation.bus.repository;

import com.project.bus_reservation.bus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query(value = "select buses.* from buses inner join routes on routes.id = buses.route_id inner join operators on " +
            "operators.id = buses.operator_id where routes.source = :source and routes.destination = :destination", nativeQuery = true)
    Optional<List<Bus>> findBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);

    @Query(value = "select * from buses where id = :busId and route_id = :routeId", nativeQuery = true)
    Optional<Bus> checkBusAndRouteExist(@Param("busId") Long busId, @Param("routeId") Long routeId);

    @Query (value = "select * from buses where id = :busId and operatorId = :operatorId", nativeQuery = true)
    Bus findBusByOperatorId(@Param("busId") Long busId, @Param("operatorId") Long operatorId);

}

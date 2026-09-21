package com.project.bus_reservation.bus.repository;

import com.project.bus_reservation.bus.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query(value = "select * from buses inner join routes on routes.id = buses.route_id inner join operators on " +
            "operators.id = buses.operator_id where routes.source = :source and routes.destination = :destination", nativeQuery = true)
    Optional<List<Bus>> findBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);

    @Query(value = "select * from buses where id = :busId and route_id = :routeId", nativeQuery = true)
    Optional<Bus> findBusByRouteId(@Param("busId") Long busId, @Param("routeId") Long routeId);

    @Query(value = "select * from buses where operator_id = :operatorId and status = 'ACTIVE'", nativeQuery = true)
    Optional<List<Bus>> findAllBusesByOperatorId(@Param("operatorId") Long operatorId);

    @Query(value = "select * from buses where operator_id = :operatorId and id = :busId and staus = 'ACTIVE'", nativeQuery = true)
    Optional<Bus> findBusByOperatorId(@Param("operatorId") Long operatorId, @Param("busId") Long busId);

    @Transactional
    @Modifying
    @Query(value = "delete from buses where id = :busId and operator_id = :operatorId", nativeQuery = true)
    int deleteBusByOperatorId(@Param("busId") Long busId, @Param("operatorId") Long operatorId);
}

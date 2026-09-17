package com.project.bus_reservation.route.repository;

import com.project.bus_reservation.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query(value = "select * from routes where id = :routeId and routes.operator_id = :operatorId", nativeQuery = true)
    Optional<Route> findRouteByBusRouteId(@Param("routeId") Long routeId, @Param("operatorId") Long operatorId);

    @Query(value = "select * from routes where operator_id = :operatorId and id = :routeId", nativeQuery = true)
    Optional<Route> findRouteByOperatorId(@Param("operatorId") Long operatorId, @Param("routeId") Long routeId);

    @Transactional
    @Modifying
    @Query(value = "delete from routes where id = :routeId and operator_id = :operatorId", nativeQuery = true)
    int deleteRouteByOperatorId(@Param("routeId") Long routeId, @Param("operatorId") Long operatorId);
}

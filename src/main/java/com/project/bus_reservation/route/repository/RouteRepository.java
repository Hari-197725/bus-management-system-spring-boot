package com.project.bus_reservation.route.repository;

import com.project.bus_reservation.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query(value = "select * from routes where routes.operator_id = :operatorId and id = :routeId", nativeQuery = true)
    Optional<Route> findRouteByBusRouteId(@Param("operatorId") Long operatorId, @Param("routeId") Long routeId);

    @Query(value = "select * from routes where operator_id = :operatorId and id = :routeId", nativeQuery = true)
    Optional<Route> findRouteByOperatorId(@Param("operatorId") Long operatorId, @Param("routeId") Long routeId);

    @Transactional
    @Modifying
    @Query(value = "delete from routes where operator_id = :operatorId and id = :routeId", nativeQuery = true)
    int deleteRouteByOperatorId(@Param("operatorId") Long operatorId, @Param("routeId") Long routeId);
}
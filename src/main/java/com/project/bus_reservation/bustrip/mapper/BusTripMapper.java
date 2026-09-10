package com.project.bus_reservation.bustrip.mapper;

import com.project.bus_reservation.bus.entity.Bus;
import com.project.bus_reservation.bustrip.dto.request.BusTripCreateRequest;
import com.project.bus_reservation.bustrip.entity.BusTrip;
import com.project.bus_reservation.operator.entity.Operator;
import com.project.bus_reservation.route.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class BusTripMapper {

    public static BusTrip toBusTripEntity(Operator operator, Bus bus, Route route, BusTripCreateRequest busTripCreateRequest){
        BusTrip busTrip = new BusTrip();
        busTrip.setDepartureTime(busTripCreateRequest.getDepartureTime());
        busTrip.setArrivalTime(busTripCreateRequest.getArrivalTime());
        busTrip.setOperator(operator);
        busTrip.setBus(bus);
        busTrip.setRoute(route);

        return busTrip;
    }

}

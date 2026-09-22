package com.project.bus_reservation.cancellation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CancellationRequest {
    private List<Integer> seatNumbers;
}
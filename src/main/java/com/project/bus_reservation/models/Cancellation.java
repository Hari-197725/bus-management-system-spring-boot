package com.project.bus_reservation.models;

import lombok.Data;

import java.util.Date;

public class Cancellation {
    private Long id;
    private Long bookingId;
    private Date cancelledAt;
    private String reason;
    private double refundAmount;
}

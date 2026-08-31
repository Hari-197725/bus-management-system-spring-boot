package com.project.bus_reservation.bustransfer.controller;

import com.project.bus_reservation.bustransfer.dto.request.BusTransferUpdateRequest;
import com.project.bus_reservation.bustransfer.service.BusTransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operator/{operatorId}/transfer")
public class BusTransferController {
    @Autowired
    BusTransferService busTransferService;

    @PutMapping
    public ResponseEntity<Void> updateBusTransfer(@PathVariable Long operatorId, @Valid @RequestBody BusTransferUpdateRequest busTransferUpdateRequest) {
        busTransferService.updateBusTransfer(operatorId, busTransferUpdateRequest);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

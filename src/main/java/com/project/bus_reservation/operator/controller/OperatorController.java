package com.project.bus_reservation.operator.controller;

import com.project.bus_reservation.operator.dto.request.OperatorRequest;
import com.project.bus_reservation.operator.dto.response.OperatorResponse;
import com.project.bus_reservation.operator.service.OperatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @PostMapping
    public ResponseEntity<Void> createOperator(@Valid @RequestBody OperatorRequest operatorRequest) {
        operatorService.createOperator(operatorRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OperatorResponse>> getAllOperators() {
        return new ResponseEntity<>(operatorService.getAllOperators(), HttpStatus.OK);
    }

    @GetMapping("/{operatorId}")
    public ResponseEntity<OperatorResponse> getOperatorById(@PathVariable Long operatorId) {
        return new ResponseEntity<>(operatorService.getOperatorById(operatorId), HttpStatus.OK);
    }


    @DeleteMapping("/{operatorId}")
    public ResponseEntity<Void> deleteOperatorById (@PathVariable Long operatorId){
        operatorService.deleteOperatorById(operatorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

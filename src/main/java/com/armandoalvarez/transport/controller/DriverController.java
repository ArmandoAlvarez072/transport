package com.armandoalvarez.transport.controller;

import com.armandoalvarez.transport.dto.request.DriverCreateRequest;
import com.armandoalvarez.transport.dto.response.DriverResponse;
import com.armandoalvarez.transport.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverResponse> create(@Valid @RequestBody DriverCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.create(request));
    }

    @GetMapping("/active")
    public ResponseEntity<List<DriverResponse>> findActive() {
        return ResponseEntity.ok(driverService.findActive());
    }
}
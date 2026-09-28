package com.armandoalvarez.transport.controller;


import com.armandoalvarez.transport.enums.OrderStatus;
import com.armandoalvarez.transport.dto.request.OrderCreateRequest;
import com.armandoalvarez.transport.dto.request.OrderStatusUpdateRequest;
import com.armandoalvarez.transport.dto.response.OrderResponse;
import com.armandoalvarez.transport.service.OrderService;
import com.armandoalvarez.transport.storage.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;
    private final FileStorageService fileStorageService;

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> changeStatus(@PathVariable UUID id,
                                                      @Valid @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.changeStatus(id, request.getStatus()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> search(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) LocalDate date,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.search(status, origin, destination, date, pageable));
    }

    @PostMapping("/{id}/assign-driver/{driverId}")
    public ResponseEntity<OrderResponse> assignDriver(@PathVariable UUID id, @PathVariable UUID driverId) {
        return ResponseEntity.ok(orderService.assignDriver(id, driverId));
    }

    @PostMapping(value = "/{id}/document", consumes = "multipart/form-data")
    public ResponseEntity<Void> uploadDocument(@PathVariable UUID id, @RequestParam MultipartFile file) {
        orderService.assignDocument(id, fileStorageService.storeDocument(file));
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<Void> uploadImage(@PathVariable UUID id, @RequestParam MultipartFile file) {
        orderService.assignImage(id, fileStorageService.storeImage(file));
        return ResponseEntity.noContent().build();
    }
}
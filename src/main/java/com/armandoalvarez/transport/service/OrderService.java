package com.armandoalvarez.transport.service;

import com.armandoalvarez.transport.enums.OrderStatus;
import com.armandoalvarez.transport.dto.request.OrderCreateRequest;
import com.armandoalvarez.transport.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.UUID;

public interface OrderService {
    OrderResponse create(OrderCreateRequest request);
    OrderResponse changeStatus(UUID orderId, OrderStatus newStatus);
    OrderResponse findById(UUID orderId);
    Page<OrderResponse> search(OrderStatus status, String origin, String destination,
                               LocalDate date, Pageable pageable);
    OrderResponse assignDriver(UUID orderId, UUID driverId);
    OrderResponse assignDocument(UUID orderId, String path);
    OrderResponse assignImage(UUID orderId, String path);
}
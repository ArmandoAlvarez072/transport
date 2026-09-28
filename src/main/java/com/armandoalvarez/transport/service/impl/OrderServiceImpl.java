package com.armandoalvarez.transport.service.impl;
import com.armandoalvarez.transport.entity.Driver;
import com.armandoalvarez.transport.entity.Order;
import com.armandoalvarez.transport.enums.OrderStatus;
import com.armandoalvarez.transport.exception.BusinessRuleViolationException;
import com.armandoalvarez.transport.exception.ResourceNotFoundException;
import com.armandoalvarez.transport.mapper.OrderMapper;
import com.armandoalvarez.transport.repository.DriverRepository;
import com.armandoalvarez.transport.repository.OrderRepository;
import com.armandoalvarez.transport.repository.spec.OrderSpecifications;
import com.armandoalvarez.transport.dto.request.OrderCreateRequest;
import com.armandoalvarez.transport.dto.response.OrderResponse;
import com.armandoalvarez.transport.service.OrderService;
import com.armandoalvarez.transport.service.OrderStatusValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final OrderMapper orderMapper;
    private final OrderStatusValidator statusValidator;

    @Override
    @Transactional
    public OrderResponse create(OrderCreateRequest request) {
        Order order = Order.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .status(OrderStatus.CREATED)
                .build();
        Order saved = orderRepository.save(order);
        log.info("Order created id={}", saved.getId());
        return orderMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public OrderResponse changeStatus(UUID orderId, OrderStatus newStatus) {
        Order order = getOrderOrThrow(orderId);
        statusValidator.validate(order.getStatus(), newStatus);
        order.setStatus(newStatus);
        log.info("Order {} transitioned to {}", orderId, newStatus);
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(UUID orderId) {
        return orderMapper.toResponse(getOrderOrThrow(orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> search(OrderStatus status, String origin, String destination,
                                       LocalDate date, Pageable pageable) {
        Specification<Order> spec = Specification
                .where(OrderSpecifications.hasStatus(status))
                .and(OrderSpecifications.hasOrigin(origin))
                .and(OrderSpecifications.hasDestination(destination))
                .and(OrderSpecifications.createdOn(date));
        return orderRepository.findAll(spec, pageable).map(orderMapper::toResponse);
    }

    @Override
    @Transactional
    public OrderResponse assignDriver(UUID orderId, UUID driverId) {
        Order order = getOrderOrThrow(orderId);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessRuleViolationException("Order must be in CREATED status to assign a driver");
        }
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found: " + driverId));
        if (!driver.isActive()) {
            throw new BusinessRuleViolationException("Driver is not active");
        }
        order.setDriver(driver);
        log.info("Driver {} assigned to order {}", driverId, orderId);
        return orderMapper.toResponse(order);
    }

    private Order getOrderOrThrow(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    @Override
    @Transactional
    public OrderResponse assignDocument(UUID orderId, String path) {
        Order order = getOrderOrThrow(orderId);
        order.setDocumentPath(path);
        log.info("Document {} assigned to order {}", path, orderId);
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse assignImage(UUID orderId, String path) {
        Order order = getOrderOrThrow(orderId);
        order.setDocumentPath(path);
        log.info("image {} assigned to order {}", path, orderId);
        return orderMapper.toResponse(order);
    }
}
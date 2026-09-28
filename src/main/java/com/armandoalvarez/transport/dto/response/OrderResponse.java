package com.armandoalvarez.transport.dto.response;

import com.armandoalvarez.transport.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class OrderResponse {
    UUID id;
    OrderStatus status;
    String origin;
    String destination;
    UUID driverId;
    String driverName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
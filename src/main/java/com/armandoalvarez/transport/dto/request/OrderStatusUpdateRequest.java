package com.armandoalvarez.transport.dto.request;

import com.armandoalvarez.transport.enums.OrderStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class OrderStatusUpdateRequest {

    @NotNull(message = "New status is required")
    private OrderStatus status;
}
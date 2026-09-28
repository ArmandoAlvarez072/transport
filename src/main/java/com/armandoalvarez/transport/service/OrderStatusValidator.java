package com.armandoalvarez.transport.service;

import com.armandoalvarez.transport.enums.OrderStatus;
import com.armandoalvarez.transport.exception.InvalidStateTransitionException;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;

@Component
public class OrderStatusValidator {

    private static final Map<OrderStatus, Set<OrderStatus>> VALID_TRANSITIONS = Map.of(
            OrderStatus.CREATED, Set.of(OrderStatus.IN_TRANSIT, OrderStatus.CANCELLED),
            OrderStatus.IN_TRANSIT, Set.of(OrderStatus.DELIVERED, OrderStatus.CANCELLED),
            OrderStatus.DELIVERED, Set.of(),
            OrderStatus.CANCELLED, Set.of()
    );

    public void validate(OrderStatus current, OrderStatus target) {
        if (!VALID_TRANSITIONS.get(current).contains(target)) {
            throw new InvalidStateTransitionException(
                    String.format("Cannot transition from %s to %s", current, target));
        }
    }
}
package com.armandoalvarez.transport.repository.spec;

import com.armandoalvarez.transport.entity.Order;
import com.armandoalvarez.transport.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class OrderSpecifications {

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Order> hasOrigin(String origin) {
        return (root, query, cb) -> origin == null ? null
                : cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%");
    }

    public static Specification<Order> hasDestination(String destination) {
        return (root, query, cb) -> destination == null ? null
                : cb.like(cb.lower(root.get("destination")), "%" + destination.toLowerCase() + "%");
    }

    public static Specification<Order> createdOn(LocalDate date) {
        return (root, query, cb) -> date == null ? null
                : cb.between(root.get("createdAt"), date.atStartOfDay(), date.atTime(23, 59, 59));
    }
}
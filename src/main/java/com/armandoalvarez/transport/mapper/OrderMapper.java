package com.armandoalvarez.transport.mapper;


import com.armandoalvarez.transport.entity.Order;
import com.armandoalvarez.transport.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "driverName", source = "driver.name")
    OrderResponse toResponse(Order order);
}
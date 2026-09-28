package com.armandoalvarez.transport.mapper;

import com.armandoalvarez.transport.entity.Driver;
import com.armandoalvarez.transport.dto.response.DriverResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponse toResponse(Driver driver);
}
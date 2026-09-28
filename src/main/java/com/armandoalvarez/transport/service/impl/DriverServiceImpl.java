package com.armandoalvarez.transport.service.impl;

import com.armandoalvarez.transport.entity.Driver;
import com.armandoalvarez.transport.mapper.DriverMapper;
import com.armandoalvarez.transport.repository.DriverRepository;
import com.armandoalvarez.transport.dto.request.DriverCreateRequest;
import com.armandoalvarez.transport.dto.response.DriverResponse;
import com.armandoalvarez.transport.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    public DriverResponse create(DriverCreateRequest request) {
        Driver driver = Driver.builder()
                .name(request.getName())
                .licenseNumber(request.getLicenseNumber())
                .active(true)
                .build();
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    @Override
    public List<DriverResponse> findActive() {
        return driverRepository.findByActiveTrue().stream()
                .map(driverMapper::toResponse)
                .toList();
    }
}
package com.armandoalvarez.transport.service;

import com.armandoalvarez.transport.dto.request.DriverCreateRequest;
import com.armandoalvarez.transport.dto.response.DriverResponse;

import java.util.List;

public interface DriverService {
    DriverResponse create(DriverCreateRequest request);
    List<DriverResponse> findActive();
}
package com.armandoalvarez.transport.dto.response;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder
public class DriverResponse {
    UUID id;
    String name;
    String licenseNumber;
    boolean active;
}
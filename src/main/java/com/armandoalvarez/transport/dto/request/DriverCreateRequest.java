package com.armandoalvarez.transport.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class DriverCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String licenseNumber;
}
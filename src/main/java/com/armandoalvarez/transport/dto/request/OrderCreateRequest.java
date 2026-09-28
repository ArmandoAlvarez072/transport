package com.armandoalvarez.transport.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class OrderCreateRequest {

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;
}
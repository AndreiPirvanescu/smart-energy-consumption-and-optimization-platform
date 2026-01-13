package com.example.smart_energy_consumption_and_optimization_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BuildingResponseDTO {
    private String name;
    private String address;
}

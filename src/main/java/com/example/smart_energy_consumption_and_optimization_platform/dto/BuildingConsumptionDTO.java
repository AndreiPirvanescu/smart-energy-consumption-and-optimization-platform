package com.example.smart_energy_consumption_and_optimization_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BuildingConsumptionDTO {
    private Long buildingId;
    private String buildingName;
    private Double totalConsumption;
    private Double totalCost;
}

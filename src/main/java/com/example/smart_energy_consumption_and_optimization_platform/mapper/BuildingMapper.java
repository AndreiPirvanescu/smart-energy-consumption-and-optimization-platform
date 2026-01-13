package com.example.smart_energy_consumption_and_optimization_platform.mapper;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import org.springframework.stereotype.Component;

@Component
public class BuildingMapper {
    public static BuildingConsumptionDTO toDTO(Building entity, Double totalConsumption, Double totalCost) {

        return BuildingConsumptionDTO.builder()
                .buildingId(entity.getId())
                .buildingName(entity.getName())
                .totalConsumption(totalConsumption)
                .totalCost(totalCost)
                .build();
    }
}

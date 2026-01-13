package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;
    private final BuildingRepository buildingRepository;

    // GET /api/buildings/{id}/consumption
    @GetMapping("/{id}/consumption")
    public BuildingConsumptionDTO getBuildingConsumption(@PathVariable Long id) {
        Building building = buildingRepository.findById(id).orElseThrow();
        Double totalConsumption = buildingService.getTotalConsumption(id);
        Double totalCost = buildingService.getConsumptionCost(id);

        return new BuildingConsumptionDTO(id, building.getName(), totalConsumption, totalCost);
    }
}

package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    // GET /api/analytics/top-buildings
    @GetMapping("/top-buildings")
    public List<BuildingConsumptionDTO> getTopBuildings() {
        return analyticsService.getTopBuildings();
    }
}

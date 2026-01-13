package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.mapper.BuildingMapper;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final BuildingRepository buildingRepository;
    private final BuildingService buildingService;

    public List<BuildingConsumptionDTO> getTopBuildings() {
        return buildingRepository.findAll()
                .stream()
                .sorted((b1, b2) -> Double.compare(
                        buildingRepository.getTotalConsumption(b2.getId()),
                        buildingRepository.getTotalConsumption(b1.getId())))
                .limit(5)
                .toList().stream()
                .map(x -> BuildingMapper.toDTO(x, buildingRepository.getTotalConsumption(x.getId()), buildingService.getConsumptionCost(x.getId())))
                .collect(Collectors.toList());
    }
}
package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.model.Tariff;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final TariffRepository tariffRepository;

    public Double getTotalConsumption(Long buildingId) {
        Double total = buildingRepository.getTotalConsumption(buildingId);
        return total != null ? total : 0.0;
    }

    public Double getConsumptionCost(Long buildingId) {
        Double consumption = getTotalConsumption(buildingId);
        Building building = buildingRepository.findById(buildingId).orElseThrow();
        Tariff tariff = tariffRepository.findById(building.getTariff().getId()).orElseThrow();
        return consumption * tariff.getRatePerKWh();
    }
}

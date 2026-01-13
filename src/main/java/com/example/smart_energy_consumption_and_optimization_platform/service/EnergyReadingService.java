package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnergyReadingService {

    private final EnergyReadingRepository repository;

    public List<EnergyReadingDTO> getAllReadings() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EnergyReadingDTO createReading(EnergyReadingDTO dto) {
        EnergyReading entity = EnergyReading.builder()
                .buildingName(dto.getBuildingName())
                .consumption(dto.getConsumption())
                .timestamp(dto.getTimestamp())
                .build();
        return toDTO(repository.save(entity));
    }

    private EnergyReadingDTO toDTO(EnergyReading entity) {
        return EnergyReadingDTO.builder()
                .id(entity.getId())
                .buildingName(entity.getBuildingName())
                .consumption(entity.getConsumption())
                .timestamp(entity.getTimestamp())
                .build();
    }
}

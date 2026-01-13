package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyMeterService {

    private final EnergyReadingRepository readingRepository;

    public EnergyReading getPeakReading(Long meterId) {
        return readingRepository.findTopByMeterIdOrderByConsumptionDesc(meterId)
                .stream().findFirst().orElse(null);
    }
}

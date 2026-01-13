package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyMeter;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyMeterRepository;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnergyReadingService {

    private final EnergyReadingRepository readingRepository;
    private final EnergyMeterRepository meterRepository;

    public List<EnergyReadingResponseDTO> getAllReadings() {
        return readingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnergyReading createReading(EnergyReadingRequestDTO energyReadingRequestDTO) {
        EnergyMeter meter = meterRepository.findById(energyReadingRequestDTO.getMeterId())
                .orElseThrow(() -> new RuntimeException("Meter not found with id " + energyReadingRequestDTO.getMeterId()));

        EnergyReading reading = new EnergyReading();
        reading.setMeter(meter);
        reading.setConsumption(energyReadingRequestDTO.getConsumption());
        reading.setTimestamp(energyReadingRequestDTO.getTimestamp());

        return readingRepository.save(reading);
    }

    public List<EnergyReadingResponseDTO> getReadingsByMeterAndRange(
            Long meterId, String start, String end) {
        return readingRepository
                .findByMeterIdAndTimestampBetween(
                        meterId,
                        LocalDateTime.parse(start),
                        LocalDateTime.parse(end))
                .stream().map(this::toDTO).toList();
    }

    private EnergyReadingResponseDTO toDTO(EnergyReading entity) {
        return EnergyReadingResponseDTO.builder()
                .id(entity.getId())
                .consumption(entity.getConsumption())
                .timestamp(entity.getTimestamp())
                .meterId(entity.getMeter().getId())
                .build();
    }
}
package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyReadingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readings")
@RequiredArgsConstructor
public class EnergyReadingController {

    private final EnergyReadingService readingService;

    @GetMapping
    public ResponseEntity<List<EnergyReadingResponseDTO>> getAllReadings() {
        return ResponseEntity.ok(readingService.getAllReadings());
    }

    @PostMapping
    public ResponseEntity<Long> createReading(@Valid @RequestBody EnergyReadingRequestDTO energyReadingRequestDTO) {
        EnergyReading saved = readingService.createReading(energyReadingRequestDTO);
        return new ResponseEntity<>(saved.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/meter/{meterId}/range")
    public ResponseEntity<List<EnergyReadingResponseDTO>> getReadingsByMeterAndRange(
            @PathVariable Long meterId,
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.ok(
                readingService.getReadingsByMeterAndRange(meterId, start, end)
        );
    }
}
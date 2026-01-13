package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingDTO;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readings")
@RequiredArgsConstructor
public class EnergyReadingController {

    private final EnergyReadingService service;

    @GetMapping
    public ResponseEntity<List<EnergyReadingDTO>> getAllReadings() {
        return ResponseEntity.ok(service.getAllReadings());
    }

    @PostMapping
    public ResponseEntity<EnergyReadingDTO> createReading(@RequestBody EnergyReadingDTO dto) {
        return ResponseEntity.ok(service.createReading(dto));
    }
}

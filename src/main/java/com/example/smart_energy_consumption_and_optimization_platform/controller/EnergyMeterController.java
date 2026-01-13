package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.PeakReadingDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyMeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meters")
@RequiredArgsConstructor
public class EnergyMeterController {

    private final EnergyMeterService meterService;

    // GET /api/meters/{id}/peak
    @GetMapping("/{id}/peak")
    public PeakReadingDTO getPeakReading(@PathVariable Long id) {
        EnergyReading peak = meterService.getPeakReading(id);
        return new PeakReadingDTO(id, peak != null ? peak.getConsumption() : 0.0);
    }
}

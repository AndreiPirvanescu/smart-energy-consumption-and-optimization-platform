package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.PeakReadingDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyMeterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meters")
@RequiredArgsConstructor
@Tag(name = "Energy Meters", description = "Endpoints for energy meter readings and analytics")
public class EnergyMeterController {

    private final EnergyMeterService meterService;

    // GET /api/meters/{id}/peak
    @GetMapping("/{id}/peak")
    @Operation(
            summary = "Get peak energy reading for a meter",
            description = "Retrieve the peak energy consumption for a specific meter by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved peak reading",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PeakReadingDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meter not found",
                    content = @Content
            )
    })
    public PeakReadingDTO getPeakReading(@PathVariable Long id) {
        EnergyReading peak = meterService.getPeakReading(id);
        return new PeakReadingDTO(id, peak != null ? peak.getConsumption() : 0.0);
    }
}

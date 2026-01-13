package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readings")
@RequiredArgsConstructor
@Tag(name = "Energy Readings", description = "Endpoints for creating and retrieving energy readings")
public class EnergyReadingController {

    private final EnergyReadingService readingService;

    @GetMapping
    @Operation(summary = "Get all energy readings", description = "Retrieve a list of all energy readings.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved readings",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EnergyReadingResponseDTO.class))
                    )
            )
    })
    public ResponseEntity<List<EnergyReadingResponseDTO>> getAllReadings() {
        return ResponseEntity.ok(readingService.getAllReadings());
    }

    @PostMapping
    @Operation(summary = "Create a new energy reading", description = "Create a new energy reading for a meter.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Energy reading created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content
            )
    })
    public ResponseEntity<Long> createReading(@Valid @RequestBody EnergyReadingRequestDTO energyReadingRequestDTO) {
        EnergyReading saved = readingService.createReading(energyReadingRequestDTO);
        return new ResponseEntity<>(saved.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/meter/{meterId}/range")
    @Operation(summary = "Get readings for a meter within a date range", description = "Retrieve energy readings for a specific meter filtered by start and end dates.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved readings",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EnergyReadingResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meter not found or no readings available",
                    content = @Content
            )
    })
    public ResponseEntity<List<EnergyReadingResponseDTO>> getReadingsByMeterAndRange(
            @PathVariable Long meterId,
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.ok(
                readingService.getReadingsByMeterAndRange(meterId, start, end)
        );
    }
}
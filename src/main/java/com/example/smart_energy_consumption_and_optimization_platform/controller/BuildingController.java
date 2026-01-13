package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
@Tag(name = "Buildings", description = "Operations related to buildings and their energy consumption")
public class BuildingController {

    private final BuildingService buildingService;
    private final BuildingRepository buildingRepository;

    // GET /api/buildings/{id}/consumption
    @GetMapping("/{id}/consumption")
    @Operation(
            summary = "Get building consumption",
            description = "Retrieve the total energy consumption and cost for a specific building by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved building consumption",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuildingConsumptionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Building not found",
                    content = @Content
            )
    })
    public ResponseEntity<BuildingConsumptionDTO> getBuildingConsumption(@PathVariable Long id) {
        return buildingRepository.findById(id)
                .map(building -> {
                    Double totalConsumption = buildingService.getTotalConsumption(id);
                    Double totalCost = buildingService.getConsumptionCost(id);
                    BuildingConsumptionDTO dto = new BuildingConsumptionDTO(id, building.getName(), totalConsumption, totalCost);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

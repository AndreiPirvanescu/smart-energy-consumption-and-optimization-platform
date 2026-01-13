package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Endpoints for analytics and energy consumption insights")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    // GET /api/analytics/top-buildings
    @Operation(
            summary = "Get top buildings by energy consumption",
            description = "Retrieve a list of buildings with the highest energy consumption."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved top buildings",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BuildingConsumptionDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No building data found",
                    content = @Content
            )
    })
    @GetMapping("/top-buildings")
    public List<BuildingConsumptionDTO> getTopBuildings() {
        return analyticsService.getTopBuildings();
    }
}

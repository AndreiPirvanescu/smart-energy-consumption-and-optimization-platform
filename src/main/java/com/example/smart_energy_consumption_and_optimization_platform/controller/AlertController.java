package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.AlertDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Alert;
import com.example.smart_energy_consumption_and_optimization_platform.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    // GET /api/alerts/building/{buildingId}
    @Tag(name = "Alerts", description = "Operations related to building alerts")
    @Operation(
            summary = "Get alerts for a building",
            description = "Retrieve a list of alerts for a specific building. " +
                    "Filters can be applied by keyword, severity, and number of days back."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved alerts",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AlertDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Building not found",
                    content = @Content
            )
    })
    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<AlertDTO>> getAlerts(
            @PathVariable Long buildingId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) Integer daysBack
    ) {
        List<Alert> alerts = alertService.getFilteredAlertsForBuilding(buildingId, keyword, severity, daysBack);
        return ResponseEntity.ok(alerts.stream()
                .map(a -> new AlertDTO(a.getId(), a.getMessage(), a.getSeverity(), a.getTimestamp()))
                .collect(Collectors.toList()));
    }
}

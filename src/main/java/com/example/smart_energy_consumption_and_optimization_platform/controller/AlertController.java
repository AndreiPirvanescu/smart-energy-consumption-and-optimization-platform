package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.AlertDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Alert;
import com.example.smart_energy_consumption_and_optimization_platform.service.AlertService;
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

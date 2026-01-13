package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.model.Alert;
import com.example.smart_energy_consumption_and_optimization_platform.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    public List<Alert> getFilteredAlertsForBuilding(Long buildingId, String keyword, String severity, Integer daysBack) {
        LocalDateTime after = LocalDateTime.now().minusDays(daysBack);
        return alertRepository.findAlertsByKeywordAndSeverityAndTimestamp(
                buildingId,
                after,
                keyword,
                severity
        );
    }
}

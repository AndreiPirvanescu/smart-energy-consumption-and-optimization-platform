package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.model.Alert;
import com.example.smart_energy_consumption_and_optimization_platform.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    @Test
    void getFilteredAlertsForBuilding_shouldPassCorrectFiltersToRepository() {
        // given
        Long buildingId = 1L;
        String keyword = "OVERLOAD";
        String severity = "HIGH";
        Integer daysBack = 7;

        Alert alert = new Alert();
        List<Alert> alerts = List.of(alert);

        when(alertRepository.findAlertsByKeywordAndSeverityAndTimestamp(
                anyLong(),
                any(LocalDateTime.class),
                anyString(),
                anyString()
        )).thenReturn(alerts);

        // when
        List<Alert> result = alertService
                .getFilteredAlertsForBuilding(buildingId, keyword, severity, daysBack);

        // then
        assertThat(result).hasSize(1);

        ArgumentCaptor<LocalDateTime> timeCaptor =
                ArgumentCaptor.forClass(LocalDateTime.class);

        verify(alertRepository).findAlertsByKeywordAndSeverityAndTimestamp(
                eq(buildingId),
                timeCaptor.capture(),
                eq(keyword),
                eq(severity)
        );

        LocalDateTime expectedLowerBound =
                LocalDateTime.now().minusDays(daysBack);

        assertThat(timeCaptor.getValue())
                .isBeforeOrEqualTo(LocalDateTime.now())
                .isAfter(expectedLowerBound.minusSeconds(2));
    }

    @Test
    void getFilteredAlertsForBuilding_shouldReturnEmptyList_whenNoAlerts() {
        when(alertRepository.findAlertsByKeywordAndSeverityAndTimestamp(
                anyLong(),
                any(LocalDateTime.class),
                any(),
                any()
        )).thenReturn(List.of());

        List<Alert> result =
                alertService.getFilteredAlertsForBuilding(1L, "X", "LOW", 1);

        assertThat(result).isEmpty();
    }
}


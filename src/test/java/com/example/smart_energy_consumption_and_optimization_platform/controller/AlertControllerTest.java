package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.model.Alert;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertController.class)
class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    private List<Alert> mockAlerts;

    @BeforeEach
    void setUp() {
        Building building = Building.builder().id(1L).name("Building A").build();

        mockAlerts = List.of(
                Alert.builder()
                        .id(1L)
                        .message("High consumption detected")
                        .severity("HIGH")
                        .timestamp(LocalDateTime.now().minusDays(1))
                        .building(building)
                        .build(),
                Alert.builder()
                        .id(2L)
                        .message("Low battery warning")
                        .severity("MEDIUM")
                        .timestamp(LocalDateTime.now().minusDays(2))
                        .building(building)
                        .build()
        );
    }

    @Test
    @DisplayName("GET /api/alerts/building/{id} with filters should return filtered alerts")
    void testGetAlerts_WithFilters() throws Exception {
        Mockito.when(alertService.getFilteredAlertsForBuilding(
                        anyLong(), anyString(), anyString(), anyInt()))
                .thenReturn(mockAlerts);

        mockMvc.perform(get("/api/alerts/building/1")
                        .param("keyword", "High")
                        .param("severity", "HIGH")
                        .param("daysBack", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockAlerts.size()))
                .andExpect(jsonPath("$[0].alertId").value(1))
                .andExpect(jsonPath("$[0].message").value("High consumption detected"))
                .andExpect(jsonPath("$[0].severity").value("HIGH"))
                .andExpect(jsonPath("$[1].alertId").value(2))
                .andExpect(jsonPath("$[1].message").value("Low battery warning"))
                .andExpect(jsonPath("$[1].severity").value("MEDIUM"));
    }

    @Test
    @DisplayName("GET /api/alerts/building/{id} without filters should return all alerts")
    void testGetAlerts_NoFilters() throws Exception {
        Mockito.when(alertService.getFilteredAlertsForBuilding(anyLong(), isNull(), isNull(), isNull()))
                .thenReturn(mockAlerts);

        mockMvc.perform(get("/api/alerts/building/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockAlerts.size()));
    }
}

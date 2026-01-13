package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void testGetTopBuildings() throws Exception {
        // Prepare mock data
        List<BuildingConsumptionDTO> topBuildings = List.of(
                new BuildingConsumptionDTO(1L, "Casa 1", 1200.5, 300.0),
                new BuildingConsumptionDTO(2L, "Casa 2", 1100.0, 275.0)
        );

        // Mock service call
        Mockito.when(analyticsService.getTopBuildings()).thenReturn(topBuildings);

        // Perform GET request and verify response
        mockMvc.perform(get("/api/analytics/top-buildings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(topBuildings.size()))
                .andExpect(jsonPath("$[0].buildingId").value(1))
                .andExpect(jsonPath("$[0].buildingName").value("Casa 1"))
                .andExpect(jsonPath("$[0].totalConsumption").value(1200.5))
                .andExpect(jsonPath("$[0].totalCost").value(300.0))
                .andExpect(jsonPath("$[1].buildingId").value(2))
                .andExpect(jsonPath("$[1].buildingName").value("Casa 2"))
                .andExpect(jsonPath("$[1].totalConsumption").value(1100.0))
                .andExpect(jsonPath("$[1].totalCost").value(275.0));
    }
}

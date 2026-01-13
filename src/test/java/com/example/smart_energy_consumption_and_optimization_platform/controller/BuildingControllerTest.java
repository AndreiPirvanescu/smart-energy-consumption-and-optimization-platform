package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.service.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuildingController.class)
class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;

    @MockBean
    private BuildingRepository buildingRepository;

    private Building sampleBuilding;

    @BeforeEach
    void setup() {
        sampleBuilding = new Building();
        sampleBuilding.setId(1L);
        sampleBuilding.setName("Casa 5");
    }

    @Test
    void testGetBuildingConsumption() throws Exception {
        // Mock repository and service
        Mockito.when(buildingRepository.findById(1L)).thenReturn(Optional.of(sampleBuilding));
        Mockito.when(buildingService.getTotalConsumption(1L)).thenReturn(500.0);
        Mockito.when(buildingService.getConsumptionCost(1L)).thenReturn(120.0);

        mockMvc.perform(get("/api/buildings/1/consumption")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buildingId").value(1))
                .andExpect(jsonPath("$.buildingName").value("Casa 5"))
                .andExpect(jsonPath("$.totalConsumption").value(500.0))
                .andExpect(jsonPath("$.totalCost").value(120.0));
    }

    @Test
    void testGetBuildingConsumption_NotFound() throws Exception {
        Mockito.when(buildingRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/buildings/2/consumption")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

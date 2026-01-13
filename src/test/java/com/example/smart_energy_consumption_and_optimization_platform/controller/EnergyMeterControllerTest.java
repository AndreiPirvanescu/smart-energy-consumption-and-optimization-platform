package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyMeterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnergyMeterController.class)
class EnergyMeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnergyMeterService meterService;

    @Autowired
    private ObjectMapper objectMapper;

    private EnergyReading sampleReading;

    @BeforeEach
    void setUp() {
        sampleReading = EnergyReading.builder()
                .id(1L)
                .consumption(150.5)
                .build();
    }

    @Test
    void testGetPeakReadingExists() throws Exception {
        Mockito.when(meterService.getPeakReading(1L)).thenReturn(sampleReading);

        mockMvc.perform(get("/api/meters/1/peak"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meterId").value(1))
                .andExpect(jsonPath("$.consumption").value(150.5)); // <-- updated
    }

    @Test
    void testGetPeakReadingNull() throws Exception {
        Mockito.when(meterService.getPeakReading(2L)).thenReturn(null);

        mockMvc.perform(get("/api/meters/2/peak"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meterId").value(2))
                .andExpect(jsonPath("$.consumption").value(0.0)); // <-- updated
    }

}

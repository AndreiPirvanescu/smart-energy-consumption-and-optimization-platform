package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyMeter;
import com.example.smart_energy_consumption_and_optimization_platform.service.EnergyReadingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnergyReadingController.class)
class EnergyReadingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnergyReadingService readingService;

    private EnergyReadingResponseDTO sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = EnergyReadingResponseDTO.builder()
                .id(1L)
                .consumption(50.0)
                .timestamp(LocalDateTime.now().plusMinutes(1))
                .meterId(100L)
                .build();
    }

    @Test
    void testGetAllReadings() throws Exception {
        Mockito.when(readingService.getAllReadings()).thenReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/readings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleResponse.getId()))
                .andExpect(jsonPath("$[0].consumption").value(sampleResponse.getConsumption()))
                .andExpect(jsonPath("$[0].meterId").value(sampleResponse.getMeterId()));
    }

    @Test
    void testCreateReading() throws Exception {
        EnergyReadingRequestDTO requestDTO = EnergyReadingRequestDTO.builder()
                .consumption(45.0)
                .timestamp(LocalDateTime.now().plusMinutes(5))
                .meterId(100L)
                .build();

        EnergyReading savedReading = EnergyReading.builder()
                .id(2L)
                .consumption(requestDTO.getConsumption())
                .timestamp(requestDTO.getTimestamp())
                .meter(EnergyMeter.builder().id(requestDTO.getMeterId()).build())
                .build();

        Mockito.when(readingService.createReading(any(EnergyReadingRequestDTO.class))).thenReturn(savedReading);

        mockMvc.perform(post("/api/readings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(String.valueOf(savedReading.getId())));
    }

    @Test
    void testGetReadingsByMeterAndRange() throws Exception {
        Mockito.when(readingService.getReadingsByMeterAndRange(anyLong(), any(), any()))
                .thenReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/readings/meter/100/range")
                        .param("start", LocalDateTime.now().toString())
                        .param("end", LocalDateTime.now().plusHours(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleResponse.getId()))
                .andExpect(jsonPath("$[0].meterId").value(sampleResponse.getMeterId()));
    }
}

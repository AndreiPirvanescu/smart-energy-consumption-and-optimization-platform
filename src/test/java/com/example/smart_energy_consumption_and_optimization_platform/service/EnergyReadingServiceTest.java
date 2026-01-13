package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyMeter;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyMeterRepository;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyReadingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnergyReadingServiceTest {

    @Mock
    private EnergyReadingRepository readingRepository;

    @Mock
    private EnergyMeterRepository meterRepository;

    @InjectMocks
    private EnergyReadingService service;

    @Test
    void getAllReadings_shouldReturnDtos() {
        EnergyMeter meter = EnergyMeter.builder().id(1L).build();

        EnergyReading reading = EnergyReading.builder()
                .id(10L)
                .consumption(100.5)
                .timestamp(LocalDateTime.now())
                .meter(meter)
                .build();

        when(readingRepository.findAll()).thenReturn(List.of(reading));

        List<EnergyReadingResponseDTO> result = service.getAllReadings();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(0).getMeterId()).isEqualTo(1L);
        assertThat(result.get(0).getConsumption()).isEqualTo(100.5);
    }

    @Test
    void createReading_shouldSaveReading_whenMeterExists() {
        EnergyMeter meter = EnergyMeter.builder().id(1L).build();

        EnergyReadingRequestDTO dto = EnergyReadingRequestDTO.builder()
                .meterId(1L)
                .consumption(50.0)
                .timestamp(LocalDateTime.now())
                .build();

        when(meterRepository.findById(1L)).thenReturn(Optional.of(meter));
        when(readingRepository.save(any(EnergyReading.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EnergyReading saved = service.createReading(dto);

        assertThat(saved.getMeter()).isEqualTo(meter);
        assertThat(saved.getConsumption()).isEqualTo(50.0);
        assertThat(saved.getTimestamp()).isEqualTo(dto.getTimestamp());
    }

    @Test
    void createReading_shouldThrowException_whenMeterNotFound() {
        EnergyReadingRequestDTO dto = EnergyReadingRequestDTO.builder()
                .meterId(99L)
                .consumption(10.0)
                .timestamp(LocalDateTime.now())
                .build();

        when(meterRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createReading(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Meter not found with id 99");

        verify(readingRepository, never()).save(any());
    }

    @Test
    void getReadingsByMeterAndRange_shouldReturnFilteredDtos() {
        EnergyMeter meter = EnergyMeter.builder().id(1L).build();

        LocalDateTime now = LocalDateTime.now();

        EnergyReading reading = EnergyReading.builder()
                .id(5L)
                .consumption(200.0)
                .timestamp(now)
                .meter(meter)
                .build();

        when(readingRepository.findByMeterIdAndTimestampBetween(
                eq(1L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(List.of(reading));

        List<EnergyReadingResponseDTO> result =
                service.getReadingsByMeterAndRange(
                        "1".equals("1") ? 1L : null,
                        now.minusDays(1).toString(),
                        now.plusDays(1).toString()
                );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getConsumption()).isEqualTo(200.0);
        assertThat(result.get(0).getMeterId()).isEqualTo(1L);
    }
}
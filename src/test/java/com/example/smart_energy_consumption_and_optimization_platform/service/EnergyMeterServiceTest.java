package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.repository.EnergyReadingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnergyMeterServiceTest {

    @Mock
    private EnergyReadingRepository readingRepository;

    @InjectMocks
    private EnergyMeterService service;

    @Test
    void getPeakReading_shouldReturnHighestConsumptionReading() {
        EnergyReading r1 = EnergyReading.builder()
                .id(1L)
                .consumption(100.0)
                .timestamp(LocalDateTime.now())
                .build();

        EnergyReading r2 = EnergyReading.builder()
                .id(2L)
                .consumption(250.0)
                .timestamp(LocalDateTime.now())
                .build();

        when(readingRepository.findTopByMeterIdOrderByConsumptionDesc(1L))
                .thenReturn(List.of(r2, r1));

        EnergyReading result = service.getPeakReading(1L);

        assertThat(result).isNotNull();
        assertThat(result.getConsumption()).isEqualTo(250.0);
    }

    @Test
    void getPeakReading_shouldReturnNull_whenNoReadingsExist() {
        when(readingRepository.findTopByMeterIdOrderByConsumptionDesc(99L))
                .thenReturn(List.of());

        EnergyReading result = service.getPeakReading(99L);

        assertThat(result).isNull();
    }
}

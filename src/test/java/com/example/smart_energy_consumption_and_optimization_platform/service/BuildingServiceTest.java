package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.model.Tariff;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private TariffRepository tariffRepository;

    @InjectMocks
    private BuildingService service;

    @Test
    void getTotalConsumption_shouldReturnValue_whenNotNull() {
        when(buildingRepository.getTotalConsumption(1L))
                .thenReturn(1500.0);

        Double result = service.getTotalConsumption(1L);

        assertThat(result).isEqualTo(1500.0);
    }

    @Test
    void getTotalConsumption_shouldReturnZero_whenNull() {
        when(buildingRepository.getTotalConsumption(2L))
                .thenReturn(null);

        Double result = service.getTotalConsumption(2L);

        assertThat(result).isEqualTo(0.0);
    }

    @Test
    void getConsumptionCost_shouldCalculateCorrectly() {
        // given
        when(buildingRepository.getTotalConsumption(1L))
                .thenReturn(100.0);

        Tariff tariff = Tariff.builder()
                .id(10L)
                .ratePerKWh(0.5)
                .build();

        Building building = Building.builder()
                .id(1L)
                .tariff(tariff)
                .build();

        when(buildingRepository.findById(1L))
                .thenReturn(Optional.of(building));

        when(tariffRepository.findById(10L))
                .thenReturn(Optional.of(tariff));

        // when
        Double cost = service.getConsumptionCost(1L);

        // then
        assertThat(cost).isEqualTo(50.0);
    }

    @Test
    void getConsumptionCost_shouldCallRepositoriesCorrectly() {
        when(buildingRepository.getTotalConsumption(1L))
                .thenReturn(200.0);

        Tariff tariff = Tariff.builder()
                .id(5L)
                .ratePerKWh(1.2)
                .build();

        Building building = Building.builder()
                .id(1L)
                .tariff(tariff)
                .build();

        when(buildingRepository.findById(1L))
                .thenReturn(Optional.of(building));

        when(tariffRepository.findById(5L))
                .thenReturn(Optional.of(tariff));

        service.getConsumptionCost(1L);

        verify(buildingRepository).getTotalConsumption(1L);
        verify(buildingRepository).findById(1L);
        verify(tariffRepository).findById(5L);
    }
}


package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingConsumptionDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private BuildingService buildingService;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void getTopBuildings_shouldReturnTopFiveSortedByConsumptionDesc() {
        // given
        Building b1 = Building.builder().id(1L).build();
        Building b2 = Building.builder().id(2L).build();
        Building b3 = Building.builder().id(3L).build();
        Building b4 = Building.builder().id(4L).build();
        Building b5 = Building.builder().id(5L).build();
        Building b6 = Building.builder().id(6L).build();

        when(buildingRepository.findAll())
                .thenReturn(List.of(b1, b2, b3, b4, b5, b6));

        when(buildingRepository.getTotalConsumption(1L)).thenReturn(100.0);
        when(buildingRepository.getTotalConsumption(2L)).thenReturn(500.0);
        when(buildingRepository.getTotalConsumption(3L)).thenReturn(300.0);
        when(buildingRepository.getTotalConsumption(4L)).thenReturn(200.0);
        when(buildingRepository.getTotalConsumption(5L)).thenReturn(400.0);
        when(buildingRepository.getTotalConsumption(6L)).thenReturn(50.0);

        when(buildingService.getConsumptionCost(anyLong()))
                .thenAnswer(invocation -> buildingRepository
                        .getTotalConsumption(invocation.getArgument(0)) * 2);

        // when
        List<BuildingConsumptionDTO> result =
                analyticsService.getTopBuildings();

        // then
        assertThat(result)
                .hasSize(5)
                .extracting(BuildingConsumptionDTO::getBuildingId)
                .containsExactly(2L, 5L, 3L, 4L, 1L); // sorted desc

        assertThat(result.get(0).getTotalConsumption()).isEqualTo(500.0);
        assertThat(result.get(0).getTotalCost()).isEqualTo(1000.0);
    }

    @Test
    void getTopBuildings_shouldCallRepositoriesCorrectly() {
        Building building = Building.builder().id(1L).build();

        when(buildingRepository.findAll())
                .thenReturn(List.of(building));

        when(buildingRepository.getTotalConsumption(1L))
                .thenReturn(100.0);

        when(buildingService.getConsumptionCost(1L))
                .thenReturn(250.0);

        analyticsService.getTopBuildings();

        verify(buildingRepository).findAll();
        verify(buildingRepository, atLeastOnce())
                .getTotalConsumption(1L);
        verify(buildingService).getConsumptionCost(1L);
    }
}

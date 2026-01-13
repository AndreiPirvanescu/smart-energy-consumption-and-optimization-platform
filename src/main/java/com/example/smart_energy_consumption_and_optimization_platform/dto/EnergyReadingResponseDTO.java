package com.example.smart_energy_consumption_and_optimization_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyReadingResponseDTO {
    private Long id;

    private double consumption;

    private LocalDateTime timestamp;

    private Long meterId;
}


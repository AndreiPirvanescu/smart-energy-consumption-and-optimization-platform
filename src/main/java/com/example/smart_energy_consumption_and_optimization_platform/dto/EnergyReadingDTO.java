package com.example.smart_energy_consumption_and_optimization_platform.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyReadingDTO {

    private Long id;
    private String buildingName;
    private double consumption;
    private LocalDateTime timestamp;
}


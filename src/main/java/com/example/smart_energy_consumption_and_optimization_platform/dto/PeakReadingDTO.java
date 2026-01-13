package com.example.smart_energy_consumption_and_optimization_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PeakReadingDTO {
    private Long meterId;
    private Double consumption;
}

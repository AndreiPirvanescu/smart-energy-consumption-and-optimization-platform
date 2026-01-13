package com.example.smart_energy_consumption_and_optimization_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AlertDTO {
    private Long alertId;
    private String message;
    private String severity;
    private LocalDateTime timestamp;
}

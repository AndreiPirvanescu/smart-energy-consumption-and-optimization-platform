package com.example.smart_energy_consumption_and_optimization_platform.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyReadingRequestDTO {

    @Min(value = 0, message = "Consumption cannot be negative")
    private double consumption;

    @NotNull(message = "Timestamp cannot be null")
    @FutureOrPresent(message = "Timestamp cannot be in the past")
    private LocalDateTime timestamp;

    @NotNull(message = "Meter ID cannot be null")
    private Long meterId;
}


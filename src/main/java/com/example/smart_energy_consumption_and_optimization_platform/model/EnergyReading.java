package com.example.smart_energy_consumption_and_optimization_platform.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "energy_readings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buildingName;

    private double consumption; // kWh

    private LocalDateTime timestamp;
}


package com.example.smart_energy_consumption_and_optimization_platform.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String message;
    private String severity;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}


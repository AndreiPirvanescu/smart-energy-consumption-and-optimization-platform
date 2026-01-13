package com.example.smart_energy_consumption_and_optimization_platform.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "buildings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<EnergyMeter> meters;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Alert> alerts;

    @ManyToOne
    @JoinColumn(name = "tariff_id", nullable = false)
    private Tariff tariff;
}


package com.example.smart_energy_consumption_and_optimization_platform.repository;

import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyMeterRepository extends JpaRepository<EnergyMeter, Long> {
}


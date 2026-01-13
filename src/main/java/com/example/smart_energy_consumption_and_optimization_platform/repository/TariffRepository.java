package com.example.smart_energy_consumption_and_optimization_platform.repository;

import com.example.smart_energy_consumption_and_optimization_platform.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
}

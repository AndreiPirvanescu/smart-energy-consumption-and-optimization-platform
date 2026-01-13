package com.example.smart_energy_consumption_and_optimization_platform.repository;

import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EnergyReadingRepository extends JpaRepository<EnergyReading, Long> {

    @Query("SELECT r FROM EnergyReading r WHERE r.meter.id = :meterId ORDER BY r.consumption DESC")
    List<EnergyReading> findTopByMeterIdOrderByConsumptionDesc(Long meterId);

    List<EnergyReading> findByMeterIdAndTimestampBetween(
            Long meterId,
            LocalDateTime start,
            LocalDateTime end
    );
}


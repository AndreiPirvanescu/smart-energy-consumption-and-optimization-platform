package com.example.smart_energy_consumption_and_optimization_platform.repository;

import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findByOwnerId(Long userId);

    @Query("SELECT SUM(r.consumption) FROM EnergyReading r WHERE r.meter.building.id = :buildingId")
    Double getTotalConsumption(Long buildingId);
}
package com.example.smart_energy_consumption_and_optimization_platform.repository;

import com.example.smart_energy_consumption_and_optimization_platform.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    @Query("""
    SELECT a FROM Alert a
    WHERE a.building.id = :buildingId
      AND a.timestamp > :after
      AND LOWER(a.message) LIKE LOWER(CONCAT('%', :keyword, '%'))
      AND (:severity = '' OR a.severity = :severity)
    ORDER BY a.timestamp DESC
    """)
    List<Alert> findAlertsByKeywordAndSeverityAndTimestamp(
            @Param("buildingId") Long buildingId,
            @Param("after") LocalDateTime after,
            @Param("keyword") String keyword,
            @Param("severity") String severity
    );


}

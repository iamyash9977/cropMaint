package com.cropmaint.repository;

import com.cropmaint.entity.MaintenanceLog;
import com.cropmaint.entity.Machine;
import com.cropmaint.entity.User;
import com.cropmaint.enums.MaintenanceLogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {

    List<MaintenanceLog> findByMachine(Machine machine);

    List<MaintenanceLog> findByStatus(MaintenanceLogStatus status);

    List<MaintenanceLog> findByMachineAndStatus(Machine machine, MaintenanceLogStatus status);

    List<MaintenanceLog> findByLogDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    Optional<MaintenanceLog> findTopByMachineOrderByLogDateDesc(Machine machine);

    List<MaintenanceLog> findByTechnician(User technician);

    List<MaintenanceLog> findByTechnicianAndStatus(User technician, MaintenanceLogStatus status);

    List<MaintenanceLog> findByMachineId(Long machineId);
}

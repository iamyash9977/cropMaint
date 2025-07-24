package com.cropmaint.repository;

import com.cropmaint.entity.Schedule;
import com.cropmaint.entity.Machine; // Import the Machine entity for relationships
import com.cropmaint.entity.User; // Import the User entity for relationships (if uncommented)
import com.cropmaint.enums.FrequencyType; // Import the enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByMachine(Machine machine);

    List<Schedule> findByActive(Boolean active);

    List<Schedule> findByMachineAndActive(Machine machine, Boolean active);

    List<Schedule> findByMaintenanceDueDateLessThanEqual(LocalDate date);

    List<Schedule> findByActiveTrueAndMaintenanceDueDateLessThanEqual(LocalDate date);

    List<Schedule> findByAssignedTechnician(User assignedTechnician);

    List<Schedule> findByAssignedTechnicianAndActiveTrue(User assignedTechnician);
}
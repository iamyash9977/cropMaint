package com.cropmaint.repository;

import com.cropmaint.entity.Machine;
import com.cropmaint.enums.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    Optional<Machine> findByMachineCode(String machineCode);

    List<Machine> findByStatus(MachineStatus status);

    List<Machine> findByLocationOrderByName(String location);

}

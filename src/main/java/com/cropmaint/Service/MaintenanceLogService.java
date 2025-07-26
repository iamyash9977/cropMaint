package com.cropmaint.service;

import com.cropmaint.dto.MaintenanceLogRequestDTO;
import com.cropmaint.dto.MaintenanceLogResponseDTO;
import com.cropmaint.entity.Machine;
import com.cropmaint.entity.MaintenanceLog;
import com.cropmaint.exception.ResourceNotFoundException;
import com.cropmaint.repository.MachineRepository;
import com.cropmaint.repository.MaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final MachineRepository machineRepository; // To check if machine exists

    @Autowired
    public MaintenanceLogService(MaintenanceLogRepository maintenanceLogRepository, MachineRepository machineRepository) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.machineRepository = machineRepository;
    }


    public MaintenanceLogResponseDTO createLog(MaintenanceLogRequestDTO requestDTO) {
        Machine machine = machineRepository.findById(requestDTO.getMachineId())
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + requestDTO.getMachineId()));

        MaintenanceLog log = mapRequestDtoToEntity(requestDTO, machine);
        MaintenanceLog savedLog = maintenanceLogRepository.save(log);
        return mapEntityToResponseDto(savedLog);
    }

    public List<MaintenanceLogResponseDTO> getAllLogs() {
        return maintenanceLogRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public List<MaintenanceLogResponseDTO> getLogsByMachineId(Long machineId) {

        if (!machineRepository.existsById(machineId)) {
            throw new ResourceNotFoundException("Machine not found with ID: " + machineId);
        }

        return maintenanceLogRepository.findByMachineId(machineId).stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public MaintenanceLogResponseDTO getLogById(Long id) {
        MaintenanceLog log = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance Log not found with ID: " + id));
        return mapEntityToResponseDto(log);
    }

    public MaintenanceLogResponseDTO updateLog(Long id, MaintenanceLogRequestDTO requestDTO) {
        MaintenanceLog existingLog = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance Log not found with ID: " + id));

        Machine machine = machineRepository.findById(requestDTO.getMachineId())
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + requestDTO.getMachineId()));

        existingLog.setLogDate(requestDTO.getLogDate());
        existingLog.setDescription(requestDTO.getDescription());
        existingLog.setPerformedBy(requestDTO.getPerformedBy());
        existingLog.setCost(requestDTO.getCost());
        existingLog.setMachine(machine);

        MaintenanceLog updatedLog = maintenanceLogRepository.save(existingLog);
        return mapEntityToResponseDto(updatedLog);
    }

    /**
     * Deletes a Maintenance Log by its ID.
     * Throws ResourceNotFoundException if the log is not found.
     * @param id The ID of the log to delete.
     */
    public void deleteLog(Long id) {
        if (!maintenanceLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance Log not found with ID: " + id);
        }
        maintenanceLogRepository.deleteById(id);
    }

    private MaintenanceLog mapRequestDtoToEntity(MaintenanceLogRequestDTO dto, Machine machine) {
        MaintenanceLog log = new MaintenanceLog();
        log.setLogDate(dto.getLogDate());
        log.setDescription(dto.getDescription());
        log.setPerformedBy(dto.getPerformedBy());
        log.setCost(dto.getCost());
        log.setMachine(machine);
        return log;
    }

    private MaintenanceLogResponseDTO mapEntityToResponseDto(MaintenanceLog log) {
        MaintenanceLogResponseDTO dto = new MaintenanceLogResponseDTO();
        dto.setId(log.getId());
        dto.setLogDate(log.getLogDate());
        dto.setDescription(log.getDescription());
        dto.setPerformedBy(log.getPerformedBy());
        dto.setCost(log.getCost());
        dto.setMachineId(log.getMachine() != null ? log.getMachine().getId() : null); // Get machine ID from entity
        return dto;
    }
}
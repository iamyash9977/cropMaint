package com.cropmaint.service;

import com.cropmaint.dto.MaintenanceLogRequestDTO;
import com.cropmaint.dto.MaintenanceLogResponseDTO;
import com.cropmaint.entity.Machine;
import com.cropmaint.entity.MaintenanceLog;
import com.cropmaint.exception.InvalidStatusTransitionException; // Import new exception
import com.cropmaint.exception.ResourceNotFoundException;
import com.cropmaint.model.MaintenanceStatus; // Import the enum
import com.cropmaint.repository.MachineRepository;
import com.cropmaint.repository.MaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final MachineRepository machineRepository;

    @Autowired
    public MaintenanceLogService(MaintenanceLogRepository maintenanceLogRepository, MachineRepository machineRepository) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.machineRepository = machineRepository;
    }

    /**
     * Creates a new Maintenance Log for a given machine.
     * Throws ResourceNotFoundException if the associated machine does not exist.
     * @param requestDTO The DTO containing maintenance log details.
     * @return The created MaintenanceLog as a ResponseDTO.
     */
    public MaintenanceLogResponseDTO createLog(MaintenanceLogRequestDTO requestDTO) {
        Machine machine = machineRepository.findById(requestDTO.getMachineId())
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + requestDTO.getMachineId()));

        MaintenanceLog log = mapRequestDtoToEntity(requestDTO, machine);

        // Set initial status if not provided, or parse if provided
        if (requestDTO.getStatus() == null || requestDTO.getStatus().trim().isEmpty()) {
            log.setStatus(MaintenanceStatus.PENDING); // Default status
        } else {
            try {
                log.setStatus(MaintenanceStatus.valueOf(requestDTO.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new InvalidStatusTransitionException("Invalid initial status: " + requestDTO.getStatus());
            }
        }

        MaintenanceLog savedLog = maintenanceLogRepository.save(log);
        return mapEntityToResponseDto(savedLog);
    }

    /**
     * Retrieves all Maintenance Logs.
     * @return A list of all MaintenanceLogs as ResponseDTOs.
     */
    public List<MaintenanceLogResponseDTO> getAllLogs() {
        return maintenanceLogRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves Maintenance Logs for a specific machine.
     * Throws ResourceNotFoundException if the machine with the given ID does not exist.
     * @param machineId The ID of the machine.
     * @return A list of MaintenanceLogs for the specified machine as ResponseDTOs.
     */
    public List<MaintenanceLogResponseDTO> getLogsByMachineId(Long machineId) {
        if (!machineRepository.existsById(machineId)) {
            throw new ResourceNotFoundException("Machine not found with ID: " + machineId);
        }
        return maintenanceLogRepository.findByMachineId(machineId).stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Maintenance Log by its ID.
     * Throws ResourceNotFoundException if the log is not found.
     * @param id The ID of the maintenance log.
     * @return The MaintenanceLog as a ResponseDTO.
     */
    public MaintenanceLogResponseDTO getLogById(Long id) {
        MaintenanceLog log = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance Log not found with ID: " + id));
        return mapEntityToResponseDto(log);
    }

    /**
     * Updates an existing Maintenance Log.
     * Throws ResourceNotFoundException if the log or associated machine is not found.
     * @param id The ID of the log to update.
     * @param requestDTO The DTO containing updated log details.
     * @return The updated MaintenanceLog as a ResponseDTO.
     */
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

        // Allow status update here as well, with validation
        if (requestDTO.getStatus() != null && !requestDTO.getStatus().trim().isEmpty()) {
            MaintenanceStatus newStatus = MaintenanceStatus.valueOf(requestDTO.getStatus().toUpperCase());
            if (!isValidTransition(existingLog.getStatus(), newStatus)) {
                throw new InvalidStatusTransitionException(
                        "Invalid status transition from " + existingLog.getStatus() + " to " + newStatus
                );
            }
            existingLog.setStatus(newStatus);
        }

        MaintenanceLog updatedLog = maintenanceLogRepository.save(existingLog);
        return mapEntityToResponseDto(updatedLog);
    }

    /**
     * Updates the status of an existing Maintenance Log.
     * Implements business logic for valid status transitions.
     * @param id The ID of the log to update.
     * @param newStatusString The new status as a string.
     * @return The updated MaintenanceLog as a ResponseDTO.
     * @throws ResourceNotFoundException if the log is not found.
     * @throws InvalidStatusTransitionException if the status transition is not allowed.
     * @throws IllegalArgumentException if the newStatusString is not a valid enum name.
     */
    public MaintenanceLogResponseDTO updateLogStatus(Long id, String newStatusString) {
        MaintenanceLog existingLog = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance Log not found with ID: " + id));

        MaintenanceStatus oldStatus = existingLog.getStatus();
        MaintenanceStatus newStatus;
        try {
            newStatus = MaintenanceStatus.valueOf(newStatusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException("Invalid status value: " + newStatusString + ". Must be one of PENDING, IN_PROGRESS, COMPLETED, CANCELED.");
        }

        if (!isValidTransition(oldStatus, newStatus)) {
            throw new InvalidStatusTransitionException(
                    "Invalid status transition for Log ID " + id + ": Cannot change from " + oldStatus + " to " + newStatus
            );
        }

        existingLog.setStatus(newStatus);
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

    // --- Helper methods for DTO to Entity mapping ---
    private MaintenanceLog mapRequestDtoToEntity(MaintenanceLogRequestDTO dto, Machine machine) {
        MaintenanceLog log = new MaintenanceLog();
        log.setLogDate(dto.getLogDate());
        log.setDescription(dto.getDescription());
        log.setPerformedBy(dto.getPerformedBy());
        log.setCost(dto.getCost());
        log.setMachine(machine);
        // Status will be set based on logic in createLog
        return log;
    }

    private MaintenanceLogResponseDTO mapEntityToResponseDto(MaintenanceLog log) {
        MaintenanceLogResponseDTO dto = new MaintenanceLogResponseDTO();
        dto.setId(log.getId());
        dto.setLogDate(log.getLogDate());
        dto.setDescription(log.getDescription());
        dto.setPerformedBy(log.getPerformedBy());
        dto.setCost(log.getCost());
        dto.setMachineId(log.getMachine() != null ? log.getMachine().getId() : null);
        dto.setStatus(log.getStatus() != null ? log.getStatus().name() : null); // Convert enum to String
        return dto;
    }

    // --- Business Logic for Status Transitions ---
    private boolean isValidTransition(MaintenanceStatus oldStatus, MaintenanceStatus newStatus) {
        if (oldStatus == newStatus) {
            return true; // No change is always valid
        }

        switch (oldStatus) {
            case PENDING:
                return newStatus == MaintenanceStatus.IN_PROGRESS || newStatus == MaintenanceStatus.CANCELED;
            case IN_PROGRESS:
                return newStatus == MaintenanceStatus.COMPLETED || newStatus == MaintenanceStatus.CANCELED;
            case COMPLETED:
            case CANCELED:
                return false; // Cannot transition from COMPLETED or CANCELED to any other state
            default:
                return false; // Should not happen
        }
    }
}
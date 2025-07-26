package com.cropmaint.controller;

import com.cropmaint.dto.MaintenanceLogRequestDTO;
import com.cropmaint.dto.MaintenanceLogResponseDTO;
import com.cropmaint.service.MaintenanceLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance-logs") // Changed base path to avoid conflict with /api/logs
@Validated
public class MaintenanceLogController {

    private final MaintenanceLogService maintenanceLogService;

    @Autowired
    public MaintenanceLogController(MaintenanceLogService maintenanceLogService) {
        this.maintenanceLogService = maintenanceLogService;
    }

    /**
     * Creates a new Maintenance Log.
     * Maps POST requests to /api/maintenance-logs
     * @param requestDTO The DTO containing maintenance log details.
     * @return ResponseEntity with the created MaintenanceLogResponseDTO and HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<MaintenanceLogResponseDTO> createLog(@Valid @RequestBody MaintenanceLogRequestDTO requestDTO) {
        MaintenanceLogResponseDTO createdLog = maintenanceLogService.createLog(requestDTO);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

    /**
     * Retrieves all Maintenance Logs, optionally filtered by machine ID.
     * Maps GET requests to /api/maintenance-logs
     * Can be called as:
     * - GET /api/maintenance-logs (get all logs)
     * - GET /api/maintenance-logs?machineId=1 (get logs for machine 1)
     * @param machineId Optional ID of the machine to filter logs by.
     * @return ResponseEntity with a list of MaintenanceLogResponseDTOs and HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<MaintenanceLogResponseDTO>> getLogs(@RequestParam(required = false) Optional<Long> machineId) {
        List<MaintenanceLogResponseDTO> logs;
        if (machineId.isPresent()) {
            logs = maintenanceLogService.getLogsByMachineId(machineId.get());
        } else {
            logs = maintenanceLogService.getAllLogs();
        }
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    /**
     * Retrieves a single Maintenance Log by its ID.
     * Maps GET requests to /api/maintenance-logs/{id}
     * @param id The ID of the maintenance log.
     * @return ResponseEntity with the MaintenanceLogResponseDTO and HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceLogResponseDTO> getLogById(@PathVariable Long id) {
        MaintenanceLogResponseDTO log = maintenanceLogService.getLogById(id);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    /**
     * Updates an existing Maintenance Log.
     * Maps PUT requests to /api/maintenance-logs/{id}
     * @param id The ID of the log to update.
     * @param requestDTO The DTO containing updated log details.
     * @return ResponseEntity with the updated MaintenanceLogResponseDTO and HTTP status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceLogResponseDTO> updateLog(@PathVariable Long id,
                                                               @Valid @RequestBody MaintenanceLogRequestDTO requestDTO) {
        MaintenanceLogResponseDTO updatedLog = maintenanceLogService.updateLog(id, requestDTO);
        return new ResponseEntity<>(updatedLog, HttpStatus.OK);
    }

    /**
     * Deletes a Maintenance Log by its ID.
     * Maps DELETE requests to /api/maintenance-logs/{id}
     * @param id The ID of the log to delete.
     * @return ResponseEntity with HTTP status 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        maintenanceLogService.deleteLog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Remember: Implement a global @ControllerAdvice for centralized exception handling
    // for ResourceNotFoundException, MethodArgumentNotValidException, etc.
}
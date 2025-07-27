package com.cropmaint.controller;

import com.cropmaint.dto.MaintenanceLogRequestDTO;
import com.cropmaint.dto.MaintenanceLogResponseDTO;
import com.cropmaint.dto.MaintenanceStatusUpdateRequestDTO;
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
@RequestMapping("/api/maintenance-logs")
@Validated
public class MaintenanceLogController {

    private final MaintenanceLogService maintenanceLogService;

    @Autowired
    public MaintenanceLogController(MaintenanceLogService maintenanceLogService) {
        this.maintenanceLogService = maintenanceLogService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceLogResponseDTO> createLog(@Valid @RequestBody MaintenanceLogRequestDTO requestDTO) {
        MaintenanceLogResponseDTO createdLog = maintenanceLogService.createLog(requestDTO);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceLogResponseDTO> getLogById(@PathVariable Long id) {
        MaintenanceLogResponseDTO log = maintenanceLogService.getLogById(id);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceLogResponseDTO> updateLog(@PathVariable Long id,
                                                               @Valid @RequestBody MaintenanceLogRequestDTO requestDTO) {
        MaintenanceLogResponseDTO updatedLog = maintenanceLogService.updateLog(id, requestDTO);
        return new ResponseEntity<>(updatedLog, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MaintenanceLogResponseDTO> updateLogStatus(@PathVariable Long id,
                                                                     @Valid @RequestBody MaintenanceStatusUpdateRequestDTO statusUpdateDTO) {
        MaintenanceLogResponseDTO updatedLog = maintenanceLogService.updateLogStatus(id, statusUpdateDTO.getStatus());
        return new ResponseEntity<>(updatedLog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        maintenanceLogService.deleteLog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
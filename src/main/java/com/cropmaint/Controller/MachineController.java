package com.cropmaint.controller;

import com.cropmaint.dto.MachineRequestDTO;
import com.cropmaint.dto.MachineResponseDTO;
import com.cropmaint.exception.DuplicateResourceException;
import com.cropmaint.exception.ResourceNotFoundException;
import com.cropmaint.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@Validated
public class MachineController {

    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping
    public ResponseEntity<MachineResponseDTO> createMachine(@Valid @RequestBody MachineRequestDTO requestDTO) {
        MachineResponseDTO createdMachine = machineService.createMachine(requestDTO);
        return new ResponseEntity<>(createdMachine, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MachineResponseDTO>> getAllMachines() {
        List<MachineResponseDTO> machines = machineService.getAllMachines();
        return new ResponseEntity<>(machines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineResponseDTO> getMachineById(@PathVariable Long id) {
        MachineResponseDTO machine = machineService.getMachineById(id);
        return new ResponseEntity<>(machine, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MachineResponseDTO> updateMachine(@PathVariable Long id,
                                                            @Valid @RequestBody MachineRequestDTO requestDTO) {
        MachineResponseDTO updatedMachine = machineService.updateMachine(id, requestDTO);
        return new ResponseEntity<>(updatedMachine, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException ex) {
         return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
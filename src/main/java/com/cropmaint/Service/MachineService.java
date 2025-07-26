package com.cropmaint.service;

import com.cropmaint.dto.MachineRequestDTO;
import com.cropmaint.dto.MachineResponseDTO;
import com.cropmaint.entity.Machine;
import com.cropmaint.exception.DuplicateResourceException;
import com.cropmaint.exception.ResourceNotFoundException;
import com.cropmaint.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public MachineResponseDTO createMachine(MachineRequestDTO requestDTO) {
        // Check if a machine with the same code already exists
        if (machineRepository.findByMachineCode(requestDTO.getMachineCode()).isPresent()) {
            throw new DuplicateResourceException("Machine with code '" + requestDTO.getMachineCode() + "' already exists.");
        }

        Machine machine = mapRequestDtoToEntity(requestDTO);
        Machine savedMachine = machineRepository.save(machine);
        return mapEntityToResponseDto(savedMachine);
    }


    public List<MachineResponseDTO> getAllMachines() {
        return machineRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public MachineResponseDTO getMachineById(Long id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + id));
        return mapEntityToResponseDto(machine);
    }

    public MachineResponseDTO updateMachine(Long id, MachineRequestDTO requestDTO) {
        Machine existingMachine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + id));

        Optional<Machine> machineWithSameCode = machineRepository.findByMachineCode(requestDTO.getMachineCode());
        if (machineWithSameCode.isPresent() && !machineWithSameCode.get().getId().equals(id)) {
            throw new DuplicateResourceException("Machine with code '" + requestDTO.getMachineCode() + "' already exists for another machine.");
        }

        existingMachine.setName(requestDTO.getName());
        existingMachine.setMachineCode(requestDTO.getMachineCode());
        existingMachine.setLocation(requestDTO.getLocation());
        existingMachine.setInstallDate(requestDTO.getInstallDate());
        existingMachine.setStatus(requestDTO.getStatus());
        existingMachine.setMachineType(requestDTO.getMachineType());
        existingMachine.setManufacturer(requestDTO.getManufacturer());
        existingMachine.setModelNumber(requestDTO.getModelNumber());
        existingMachine.setSerialNumber(requestDTO.getSerialNumber());
        existingMachine.setCriticalityLevel(requestDTO.getCriticalityLevel());

        Machine updatedMachine = machineRepository.save(existingMachine);
        return mapEntityToResponseDto(updatedMachine);
    }

    public void deleteMachine(Long id) {
        if (!machineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Machine not found with ID: " + id);
        }
        machineRepository.deleteById(id);
    }


    private Machine mapRequestDtoToEntity(MachineRequestDTO dto) {
        Machine machine = new Machine();
        machine.setName(dto.getName());
        machine.setMachineCode(dto.getMachineCode());
        machine.setLocation(dto.getLocation());
        machine.setInstallDate(dto.getInstallDate());
        machine.setStatus(dto.getStatus());
        machine.setMachineType(dto.getMachineType());
        machine.setManufacturer(dto.getManufacturer());
        machine.setModelNumber(dto.getModelNumber());
        machine.setSerialNumber(dto.getSerialNumber());
        machine.setCriticalityLevel(dto.getCriticalityLevel());
        return machine;
    }

    private MachineResponseDTO mapEntityToResponseDto(Machine machine) {
        MachineResponseDTO dto = new MachineResponseDTO();
        dto.setId(machine.getId());
        dto.setName(machine.getName());
        dto.setMachineCode(machine.getMachineCode());
        dto.setLocation(machine.getLocation());
        dto.setInstallDate(machine.getInstallDate());
        dto.setStatus(machine.getStatus());
        dto.setMachineType(machine.getMachineType());
        dto.setManufacturer(machine.getManufacturer());
        dto.setModelNumber(machine.getModelNumber());
        dto.setSerialNumber(machine.getSerialNumber());
        dto.setCriticalityLevel(machine.getCriticalityLevel());
        return dto;
    }
}
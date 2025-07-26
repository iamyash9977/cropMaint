package com.cropmaint.dto;

import com.cropmaint.enums.CriticalityLevel;
import com.cropmaint.enums.MachineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineResponseDTO {

    private Long id;

    private String name;
    private String machineCode;
    private String location;
    private LocalDate installDate;
    private MachineStatus status;
    private String machineType;
    private String manufacturer;
    private String modelNumber;
    private String serialNumber;
    private CriticalityLevel criticalityLevel;
    private int numberOfMaintenanceLogs;
    private int numberOfSchedules;
}
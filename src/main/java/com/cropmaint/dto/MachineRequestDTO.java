package com.cropmaint.dto;

import com.cropmaint.enums.CriticalityLevel;
import com.cropmaint.enums.MachineStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineRequestDTO {


    @NotBlank(message = "Machine name cannot be blank")
    @Size(max = 255, message = "Machine name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Machine code cannot be blank")
    @Size(max = 255, message = "Machine code cannot exceed 255 characters")
    private String machineCode;

    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @PastOrPresent(message = "Install date cannot be in the future")
    private LocalDate installDate;

    @NotNull(message = "Machine status cannot be null")
    private MachineStatus status;

    @Size(max = 255, message = "Machine type cannot exceed 255 characters")
    private String machineType;

    @Size(max = 255, message = "Manufacturer cannot exceed 255 characters")
    private String manufacturer;

    @Size(max = 255, message = "Model number cannot exceed 255 characters")
    private String modelNumber;

    @Size(max = 255, message = "Serial number cannot exceed 255 characters")
    private String serialNumber;

    @NotNull(message = "Criticality level cannot be null")
    private CriticalityLevel criticalityLevel;

}
package com.cropmaint.dto;

import java.time.LocalDate;
import com.cropmaint.enums.CriticalityLevel;
import com.cropmaint.enums.MachineStatus;

public class MachineRequestDTO {
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

    // Constructors
    public MachineRequestDTO() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getInstallDate() {
        return installDate;
    }

    public void setInstallDate(LocalDate installDate) {
        this.installDate = installDate;
    }


    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public MachineStatus getStatus() {
        return status;
    }
    public void setStatus(MachineStatus status) {
        this.status = status;
    }

    public CriticalityLevel getCriticalityLevel() {
        return criticalityLevel;
    }
    public void setCriticalityLevel(CriticalityLevel criticalityLevel) {
        this.criticalityLevel = criticalityLevel;
    }
}
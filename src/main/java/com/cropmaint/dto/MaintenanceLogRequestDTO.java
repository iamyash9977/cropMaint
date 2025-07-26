package com.cropmaint.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class MaintenanceLogRequestDTO {

    @NotNull(message = "Log date cannot be null")
    @PastOrPresent(message = "Log date cannot be in the future")
    private LocalDate logDate;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Performed by cannot be blank")
    private String performedBy;

    @DecimalMin(value = "0.0", inclusive = true, message = "Cost cannot be negative")
    private Double cost;

    @NotNull(message = "Machine ID cannot be null")
    private Long machineId; // To link to the Machine

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}
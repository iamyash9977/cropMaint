package com.cropmaint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MaintenanceStatusUpdateRequestDTO {

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED|CANCELED", message = "Invalid status value. Must be PENDING, IN_PROGRESS, COMPLETED, or CANCELED.")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
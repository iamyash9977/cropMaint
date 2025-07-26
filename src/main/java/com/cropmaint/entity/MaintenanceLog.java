package com.cropmaint.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_logs")
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "log_date")
    private LocalDate logDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "performed_by")
    private String performedBy;

    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    public MaintenanceLog() {
    }

    public MaintenanceLog(LocalDate logDate, String description, String performedBy, Double cost, Machine machine) {
        this.logDate = logDate;
        this.description = description;
        this.performedBy = performedBy;
        this.cost = cost;
        this.machine = machine;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    @Override
    public String toString() {
        return "MaintenanceLog{" +
                "id=" + id +
                ", logDate=" + logDate +
                ", description='" + description + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", cost=" + cost +
                ", machineId=" + (machine != null ? machine.getId() : "null") +
                '}';
    }
}
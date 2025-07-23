package com.cropmaint.entity;

import com.cropmaint.enums.FrequencyType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maintenance_task_description", nullable = false)
    private String maintenanceTaskDescription;

    @Column(name = "maintenance_due_date", nullable = false)
    private LocalDate maintenanceDueDate;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "frequency_days", nullable = false)
    private Integer frequencyDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency_type")
    private FrequencyType frequencyType;

    @Column(name = "last_performed_date")
    private LocalDate lastPerformedDate;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_technician_id")
    private User assignedTechnician;

}

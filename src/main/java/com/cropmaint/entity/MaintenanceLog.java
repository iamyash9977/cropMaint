package com.cropmaint.entity;

import com.cropmaint.enums.MaintenanceLogStatus;
import com.cropmaint.enums.MaintenanceType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_description", columnDefinition = "TEXT", nullable = false)
    private String issueDescription;

    @Column(name = "maintenance_date", nullable = false)
    private LocalDateTime maintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_type", nullable = false)
    private MaintenanceType maintenanceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceLogStatus status;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private User technician;
}
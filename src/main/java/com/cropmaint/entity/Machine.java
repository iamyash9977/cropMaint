package com.cropmaint.entity;

import com.cropmaint.enums.CriticalityLevel;
import com.cropmaint.enums.MachineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "machine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "machine_code", unique = true, nullable = false, length = 255)
    private String machineCode;

    private String location;

    @Column(name = "install_date")
    private LocalDate installDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MachineStatus status;

    @Column(name = "machine_type")
    private String machineType;

    private String manufacturer;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality_level")
    private CriticalityLevel criticalityLevel;


    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MaintenanceLog> maintenanceLogs = new HashSet<>();

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Schedule> schedules = new HashSet<>();

}


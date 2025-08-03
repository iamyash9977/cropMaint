package com.cropmaint.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.cropmaint.enums.MachineStatus;
import com.cropmaint.enums.CriticalityLevel;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String machineCode;

    private String location;
    private LocalDate installDate;
    private String machineType;
    private String manufacturer;
    private String modelNumber;
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    @Enumerated(EnumType.STRING)
    private CriticalityLevel criticalityLevel;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceLog> maintenanceLogs = new ArrayList<>();

    // Default constructor
    public Machine() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public List<MaintenanceLog> getMaintenanceLogs() {
        return maintenanceLogs;
    }

    public void setMaintenanceLogs(List<MaintenanceLog> maintenanceLogs) {
        this.maintenanceLogs = maintenanceLogs;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", machineCode='" + machineCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return Objects.equals(id, machine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
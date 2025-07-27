package com.cropmaint.entity;

import com.cropmaint.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;


    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceLog> assignedLogs = new ArrayList<>();


    @OneToMany(mappedBy = "assignedTechnician", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Schedule> assignedSchedules = new HashSet<>();

}
package com.cropmaint.repository;

import com.cropmaint.entity.User;
import com.cropmaint.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);
}
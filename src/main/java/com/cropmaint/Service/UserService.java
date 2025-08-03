package com.cropmaint.service;

import com.cropmaint.dto.UserRequestDTO;
import com.cropmaint.dto.UserResponseDTO;
import com.cropmaint.entity.User;
import com.cropmaint.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userRequestDTO.getUsername());
        }
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userRequestDTO.getEmail());
        }

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setRole(userRequestDTO.getRole());
        // Hash the password before saving
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (!existingUser.getUsername().equals(userRequestDTO.getUsername()) && userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userRequestDTO.getUsername());
        }
        if (!existingUser.getEmail().equals(userRequestDTO.getEmail()) && userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userRequestDTO.getEmail());
        }


        existingUser.setUsername(userRequestDTO.getUsername());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setRole(userRequestDTO.getRole());
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO convertToDto(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
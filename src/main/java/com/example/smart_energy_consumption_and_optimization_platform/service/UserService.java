package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.CreateUserRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.EnergyReadingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.UserResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.model.EnergyReading;
import com.example.smart_energy_consumption_and_optimization_platform.model.User;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;

    public List<BuildingResponseDTO> getBuildingsByUser(Long userId) {
        return buildingRepository.findByOwnerId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private BuildingResponseDTO toDTO(Building entity) {
        return BuildingResponseDTO.builder()
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }


}
